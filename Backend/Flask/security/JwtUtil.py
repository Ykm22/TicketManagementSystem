import jwt
from jwt import decode

class JwtUtil:
    SECRET_KEY = "0123456789abcdef0123456789abcdef0123456789abcdef"

    @staticmethod
    def extract_email(token):
        try:
            payload = jwt.decode(token, JwtUtil.SECRET_KEY, algorithms=['HS256'], options={"verify_signature": False})
            return payload.get('sub')
        except Exception as e:
            print(e)
            return None