module ExceptionHelper
  extend ActiveSupport::Concern

  class AuthenticationError < StandardError; end
  class MissingToken < StandardError; end
  class InvalidToken < StandardError; end

  included do
    rescue_from ActiveRecord::RecordNotFound, with: :not_found_message
    rescue_from ActiveRecord::RecordInvalid, with: :unprocessable
    rescue_from ExceptionHelper::AuthenticationError, with: :unauthorized_request
    rescue_from ExceptionHelper::MissingToken, with: :unprocessable
    rescue_from ExceptionHelper::InvalidToken, with: :unprocessable
  end


  def unauthorized_request(exception)
    json_response({message: exception.message}, :unauthorized)
  end

  def not_found_message(exception)
    json_response({message: exception.message}, :not_found)
  end

  def unprocessable(exception)
    json_response({message: exception.message}, :unprocessable_entity)
  end

end