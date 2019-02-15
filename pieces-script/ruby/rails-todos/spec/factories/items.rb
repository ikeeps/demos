FactoryBot.define do
  factory :item do
    name { Faker::StarWars.quote }
    done { false }
    todo_id { nil }
  end
end