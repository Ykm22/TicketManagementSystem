import matplotlib.pyplot as plt
from datetime import datetime
from models.SalesModel import SalesModel
import sys
import io

class GraphPredictionPlotter():
    def __init__(self):
        sys.path.append('../../')
        self.model = SalesModel()

    def predict_winnings_for_current_year(self):
        
        from data.DbUtils import DbUtils
        dbUtils = DbUtils()
        current_date = datetime.now().date()

        plt.figure(figsize=(10, 6))
        line_colors = ['#de411b', 'green']

        year = current_date.year
        no_months = current_date.month

        monthly_winnings_list = []
        for month in range(1, no_months + 1):
            # print(f'month = {month}')
            events = dbUtils.get_events_by_year_month(year, month)
            monthly_winnings = 0
            for event in events:
                final_winnings = dbUtils.get_final_winnings_for_event(event)
                monthly_winnings += final_winnings
            monthly_winnings_list.append(monthly_winnings)
        predicted_monthly_winnings_list = []
        
        for month in range(no_months + 1, 13):
            predicted_monthly_winnings = self.model.predict_sale_for_year_month(year, month)
            predicted_monthly_winnings_list.append(predicted_monthly_winnings)
        monthly_winnings_list.append(predicted_monthly_winnings_list[0])
        plt.plot(
            range(1, no_months + 2),
            monthly_winnings_list,
            marker='o',
            color=line_colors[0],
            label=str(year) + ' So far'
        )
        plt.plot(
            range(no_months + 1, 13),
            predicted_monthly_winnings_list,
            marker='o',
            linestyle='--',
            color=line_colors[1],
            label=str(year) + ' Predicted'
        )
        plt.xlabel('Month')
        plt.ylabel('Winnings')
        plt.title('Monthly Winnings Over The Year(s)')
        plt.legend()
        plt.grid(True)  # Adding grid lines

        # Adding x-axis labels for each month
        month_labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        plt.xticks(range(1, 13), month_labels[:12])

        ax = plt.gca()
        ax.set_facecolor('#f4f4f4')

        plt.tight_layout()
        fig = plt.gcf()
        # plt.show()
        img_stream = io.BytesIO()
        # fig.savefig('plot_by_years.png', dpi=100)
        fig.savefig(img_stream, format='png', dpi=100)
        plt.clf()
        # plt.close() not thread safe

        img_stream.seek(0)
        return img_stream