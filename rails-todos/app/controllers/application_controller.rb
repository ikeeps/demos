class ApplicationController < ActionController::API
  include ResponseHelper
  include ExceptionHelper

  before_action :authorize_request 

  attr_reader :current_user 

  private

  def authorize_request
    @current_user = (AuthorizedRequestApi.new(request.headers).call)[:user]
  end

end
