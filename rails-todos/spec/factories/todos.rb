FactoryBot.define do
  factory :todo do
    title { Faker::OnePiece.character}
    created_by { Faker::Number.number(10) }
  end
end