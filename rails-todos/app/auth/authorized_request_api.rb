class AuthorizedRequestApi
  def initialize(headers)
    @headers = headers
  end

  def call
    {
      user: user
    }
  end

  private

  attr_reader :headers

  def user
    @user ||= User.find(decoded_auth_token[:user_id]) if decoded_auth_token
  rescue ActiveRecord::RecordNotFound => e
    raise(ExceptionHelper::InvalidToken, "#{Message.invalid_token} #{e.message}")
  end

  def decoded_auth_token
    @decoded_auth_token ||= JsonWebToken.decode(http_authorization_header)
  end

  def http_authorization_header
    if headers['Authorization'].present?
      return headers['Authorization'].split(' ').last
    else
      raise(ExceptionHelper::MissingToken, Message.missing_token)
    end
  end
end