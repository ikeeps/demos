module ExceptionHelper
  extend ActiveSupport::Concern

  included do
    rescue_from ActiveRecord::RecordNotFound, with: :not_found_message
    rescue_from ActiveRecord::RecordInvalid, with: :unprocessable
  end

  def not_found_message(exception)
    json_response({message: exception.message}, :not_found)
  end

  def unprocessable(exception)
    json_response({message: exception.message}, :unprocessable_entity)
  end

end