from keras.models import load_model
import numpy as np

class SalesModel():
    def __init__(self):
        self.model = load_model("model_predict_sales_for_year_month.h5")
        self.window_size = 12

    def predict_sale_for_year_month(self, year, month):
        # Prepare input data for prediction (example for a specific year and month)
        input_sequence = []
        for _ in range(self.window_size):
            month = month - 1
            if month == 1:
                year = year - 1
                month = 12
            input_sequence.append((year, month))

        # Convert input data to numpy array and reshape for LSTM input
        input_sequence = np.array(input_sequence).reshape(1, self.window_size, 2)

        # Make predictions for the specified year and month
        predicted_sales = self.model.predict(input_sequence)
        return predicted_sales[0][0]