import matplotlib.pyplot as plt
from datetime import datetime
import sys
import io

class GraphPlotter():
    def __init__(self):
        sys.path.append('../../')

    def print_winnings_graph_for_years(self, years):
        from data.DbUtils import DbUtils
        dbUtils = DbUtils()
        current_date = datetime.now().date()

        plt.figure(figsize=(10, 6))
        line_colors = ['#de411b', 'green', 'purple', 'orange', 'blue']

        # for final plot, to not have empty x ticks
        Min_winnings = 1e9
        
        for i, year in enumerate(years):
            monthly_winnings_list = []
            max_month = 12
            if year == str(current_date.year):
                max_month = current_date.month
            for month in range(1, max_month + 1):
                events = dbUtils.get_events_by_year_month(year, month)
                monthly_winnings = 0
                for event in events:
                    final_winnings = dbUtils.get_final_winnings_for_event(event)
                    monthly_winnings += final_winnings
                if monthly_winnings < Min_winnings:
                    Min_winnings = monthly_winnings
                monthly_winnings_list.append(monthly_winnings)

            plt.plot(
                range(1, max_month + 1),
                monthly_winnings_list,
                marker='o',
                color=line_colors[i % len(line_colors)],
                label=str(year)
            )
        plt.plot(range(1, 13), [Min_winnings] * 12, linestyle='-', color=(0, 0, 0, 0))
        
        plt.xlabel('Month')
        plt.ylabel('Winnings')
        plt.title('Monthly Winnings Over The Year(s)')
        plt.legend()
        plt.grid(True)  # Adding grid lines

        # Adding x-axis labels for each month
        month_labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        plt.xticks(range(1, 12 + 1), month_labels[:12])

        ax = plt.gca()
        ax.set_facecolor('#f4f4f4')

        plt.tight_layout()
        fig = plt.gcf()
        # plt.show()
        img_stream = io.BytesIO()
        # fig.savefig('plot_by_years.png', dpi=100)
        fig.savefig(img_stream, format='png', dpi=100)
        plt.clf()
        # plt.close() not thread safe = 

        img_stream.seek(0)
        return img_stream
    
if __name__ == "__main__":
    plt.figure(figsize=(10, 6))
    plt.xlabel('Month')
    plt.ylabel('Winnings')
    plt.title('Monthly Winnings Over The Year(s)')
    plt.grid(True)  # Adding grid lines

    # Adding x-axis labels for each month
    month_labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    plt.xticks(range(1, 13), month_labels[:12])

    ax = plt.gca()
    ax.set_facecolor('#f4f4f4')

    plt.tight_layout()
    plt.plot(range(1, 13), [0] * 12, linestyle='-', color=(0, 0, 0, 0))
    plt.savefig("test.png")
        