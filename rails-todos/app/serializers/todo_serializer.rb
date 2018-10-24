class TodoSerializer < ActiveModel::Serializer
  attributes :id, :title, :updated_at, :created_at, :created_by

  has_many :items
end
