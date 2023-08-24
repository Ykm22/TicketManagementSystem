import pyodbc

class DbUtils:
    def __init__(self):
        server = 'ICHIM\SQLEXPRESS03'
        database = 'TicketManagementSystem'
        username = ''
        password = ''
        connection_string = f'DRIVER={{SQL Server}};SERVER={server};DATABASE={database};UID={username};PWD={password}'
        self.conn = pyodbc.connect(connection_string)
 
    def get_events_by_year(self, year):
        cursor = self.conn.cursor()
        query = 'SELECT * FROM Events WHERE YEAR(StartDate) = ?'
        cursor.execute(query, (year,))
        events = cursor.fetchall()
        return events

    def get_ticket_categories_for_event(self, event):
        cursor = self.conn.cursor()
        query = 'SELECT * FROM TicketCategories WHERE Event_ID = ?'
        cursor.execute(query, (event[0]))
        ticket_categories = cursor.fetchall()
        return ticket_categories

    def get_total_winnings_for_event(self, event):
        ticket_categories = self.get_ticket_categories_for_event(event)
        cursor = self.conn.cursor()
        winnings = 0
        for ticket_category in ticket_categories:
            query = 'SELECT TotalPrice FROM Orders WHERE TicketCategories_ID = ?'
            cursor.execute(query, (ticket_category[0]))
            orders = cursor.fetchall()
            for order in orders:
                winnings += order[0]
        return winnings

    def get_hours_for_event(self, event):
        start_date = event[5]
        end_date = event[6]
        time_difference = end_date - start_date
        total_hours = time_difference.days * 24 + time_difference.seconds // 3600
        return total_hours

    def get_price_per_hour_for_event(self, event):
        query = 'SELECT PricePerHour FROM Venues WHERE ID = ?'
        cursor = self.conn.cursor()
        cursor.execute(query, (event[1]))
        return cursor.fetchone()

    def get_total_cost_for_event(self, event):
        hours = self.get_hours_for_event(event)
        price_per_hour = self.get_price_per_hour_for_event(event)[0] # first column of entry
        return hours * price_per_hour

    def get_final_winnings_for_event(self, event):
        cost = self.get_total_cost_for_event(event)
        winnings = self.get_total_winnings_for_event(event)
        return winnings - cost
    
    def get_events_by_year_month(self, year, month):
        query = 'SELECT * FROM Events WHERE YEAR(StartDate) = ? AND MONTH(StartDate) = ?'
        cursor = self.conn.cursor()
        cursor.execute(query, (year, month))

        events = cursor.fetchall()
        return events
        
    def get_event_types(self):
        query = '''
                SELECT Name FROM EventTypes  
                '''
        cursor = self.conn.cursor()
        cursor.execute(query)
        eventTypes = cursor.fetchall()
        eventTypes = [eventType[0] for eventType in eventTypes]
        return eventTypes

    def get_ages_for_eventType_and_sex(self, eventType, sex):
        query = '''
                SELECT U.Age, U.ID FROM Users U  
                INNER JOIN Orders O ON O.User_ID = U.ID 
                INNER JOIN TicketCategories TC ON TC.ID = O.TicketCategories_ID 
                INNER JOIN Events E ON E.ID = TC.Event_ID 
                INNER JOIN EventTypes ET ON ET.ID = E.EventType_ID 
                WHERE ET.Name = ? 
                AND U.Sex =  ?
                GROUP BY U.ID, U.Age  --To avoid multiple orders from same user
                '''
        cursor = self.conn.cursor()
        cursor.execute(query, (eventType, sex))
        user_ages = cursor.fetchall()
        user_ages = [user_age[0] for user_age in user_ages]
        return user_ages
    
    def get_age_percentages_for_eventType_and_sex(self, eventType, sex) -> dict:
        age_groups_boundaries = { # Age groups
            1: [5, 17], # Group 1
            2: [18, 25], # Group 2
            3: [26, 40], # Group 3
            4: [41, 100] # Group 4
        }
        user_ages = self.get_ages_for_eventType_and_sex(eventType, sex)
        no_users = len(user_ages)
        age_percentages = {}
        for age_group, age_boundaries in age_groups_boundaries.items():
            no_ages = 0
            for user_age in user_ages:
                if user_age >= age_boundaries[0] and user_age <= age_boundaries[1]:
                    no_ages += 1

            age_percentages[age_group] = round(no_ages / no_users, 3)
        return age_percentages

if __name__ == '__main__':
    dbUtils = DbUtils()
    user_ages = dbUtils.get_age_percentages_for_eventType_and_sex("Workshop", "f")
    event_types = dbUtils.get_event_types()
