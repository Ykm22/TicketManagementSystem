from flask import Flask, send_file, request, jsonify, Response
from flask_cors import CORS
import sys 
from plotting.GraphPlotter import GraphPlotter
from plotting.GraphPredictionPlotter import GraphPredictionPlotter
from security.MyCustomSecurity import MyCustomSecurity, UserRoles, NoAuthorizationException, ResourceForbiddenException
from data.DbUtils import DbUtils
plotter = GraphPlotter()
predictionPlotter = GraphPredictionPlotter()

app = Flask(__name__)

CORS(app, resources={r"/*": {
    "origins": '*'
    }})

dbUtils = DbUtils()

@app.route('/')
def hello_world():
    return "hello world"

@app.route('/event_years')
def get_events_dates():
    return {"years": [2020, 2019, 2021, 2022, 2023]}

@app.route('/draw_graph_years', methods=['POST'])
def draw_graph_years():
    try:
        MyCustomSecurity.validate_request(request, UserRoles.ADMIN)
        data = request.json
        years = data['years']
        img_stream = plotter.print_winnings_graph_for_years(years)
        response = Response(img_stream.read(), content_type='image/png')
        return response
    except NoAuthorizationException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 401
    
    except ResourceForbiddenException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 403
    
    except KeyError:
        error_message = {"error": "Invalid JSON data"}
        return jsonify(error_message), 400

@app.route('/predict_winnings')
def draw_predicted_winnings_graph():
    try:
        MyCustomSecurity.validate_request(request, UserRoles.ADMIN)
        img_stream = predictionPlotter.predict_winnings_for_current_year()
        response = Response(img_stream.read(), content_type='image/png')
        return response
    except NoAuthorizationException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 401
    
    except ResourceForbiddenException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 403
    
    except KeyError:
        error_message = {"error": "Invalid JSON data"}
        return jsonify(error_message), 400

@app.route('/event_types')
def get_event_types():
    try:
        MyCustomSecurity.validate_request(request, UserRoles.ADMIN)
        event_types = dbUtils.get_event_types()
        return jsonify({"event_types": event_types})
    
    except NoAuthorizationException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 401
    
    except ResourceForbiddenException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 403
    
    except KeyError:
        error_message = {"error": "Invalid JSON data"}
        return jsonify(error_message), 400

@app.route('/actual_age_percentages')
def get_actual_age_percentages():
    try:
        MyCustomSecurity.validate_request(request, UserRoles.ADMIN)
        event_type = request.args.get("event_type")
        age_percentages = {}
        for sex in ["m", "f"]:
            age_groups_percentages = dbUtils.get_age_percentages_for_eventType_and_sex(event_type, sex)
            age_percentages[sex] = age_groups_percentages
            
        return jsonify(age_percentages)
    
    except NoAuthorizationException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 401
    
    except ResourceForbiddenException as e:
        error_message = {"error": str(e)}
        return jsonify(error_message), 403
    
    except KeyError:
        error_message = {"error": "Invalid JSON data"}
        return jsonify(error_message), 400

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=7070)