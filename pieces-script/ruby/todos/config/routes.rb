Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  resources :todos
  post "versions/:id/revert" => "versions#revert", :as => "revert_version"
end
