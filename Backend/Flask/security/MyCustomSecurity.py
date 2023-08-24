from JwtUtil import JwtUtil
from data.UsersRepository import UsersRepository
class UserRoles:
    CUSTOMER = "CUSTOMER"
    ADMIN = "ADMIN"

class NoAuthorizationException(Exception):
    pass
class ResourceForbiddenException(Exception):
    pass

class MyCustomSecurity:
    users_repository = UsersRepository()

    @staticmethod
    def validate_request(context, user_role):
        if not MyCustomSecurity.request_contains_authorization(context):
            raise NoAuthorizationException("No authorization header in request!")

        jwt_token = MyCustomSecurity.extract_jwt(context)
        email = JwtUtil.extract_email(jwt_token)
        is_customer = MyCustomSecurity.get_user_role(email)
        if is_customer and user_role != UserRoles.CUSTOMER:
            raise ResourceForbiddenException("Resource not allowed!")

        if not is_customer and user_role != UserRoles.ADMIN:
            raise ResourceForbiddenException("Resource not allowed!")

    @staticmethod
    def request_contains_authorization(request):
        return "Authorization" in request.headers

    @staticmethod
    def extract_jwt(request):
        authorization_header = request.headers["Authorization"]

        if not authorization_header.startswith("Bearer"):
            raise NoAuthorizationException("Authorization header not starting with Bearer!")

        return authorization_header[7:]

    @staticmethod
    def get_user_role(email):
        return MyCustomSecurity.users_repository.find_role_by_email(email)
