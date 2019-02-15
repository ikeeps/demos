class V2::TodosController < ApplicationController
  def index
    json_response({ message: "hello in v2"})
  end
end
