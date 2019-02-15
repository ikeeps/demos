class JsonWebToken
  HMAC_SECRET = Rails.application.credentials.secret_key_base

  def self.encode(payload, expired = 24.hours.from_now)
    payload[:exp] = expired.to_i
    JWT.encode(payload, HMAC_SECRET)
  end

  def self.decode(token)
    payload = JWT.decode(token, HMAC_SECRET)[0]
    HashWithIndifferentAccess.new payload
  rescue JWT::DecodeError => e
    raise(ExceptionHelper::InvalidToken, e.message)
  end
end
