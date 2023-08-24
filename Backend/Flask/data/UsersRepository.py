import pyodbc

class UsersRepository:
    def __init__(self):
        server = 'ICHIM\SQLEXPRESS03'
        database = 'TicketManagementSystem'
        username = ''
        password = ''
        self.connection_string = f'DRIVER={{SQL Server}};SERVER={server};DATABASE={database};UID={username};PWD={password}'

    def find_role_by_email(self, email):
        with pyodbc.connect(self.connection_string) as connection:
            with connection.cursor() as cursor:
                print("enter")
                cursor.execute(
                    "SELECT isCustomer FROM Users WHERE email = ?", email)
                row = cursor.fetchone()
                if row is None:
                    raise Exception("User email not found")
                return row.isCustomer