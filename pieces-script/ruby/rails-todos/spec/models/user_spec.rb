require 'rails_helper'
require 'benchmark'

RSpec.describe User, type: :model do
  it { should validate_presence_of(:name) }
  it { should validate_presence_of(:email) }
  it { should validate_presence_of(:password_digest) }

  it { should have_many(:todos) }

  how_long = Benchmark.measure do
    (1..100) { |i| i }
  end
  puts how_log
end
