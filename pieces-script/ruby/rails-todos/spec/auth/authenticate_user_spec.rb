require 'rails_helper'

RSpec.describe AuthenticateUser do
  let!(:user) { create(:user) }
  subject(:valid_request) { described_class.new(user.email, user.password) }
  subject(:invalid_request) { described_class.new("foo", "bar") }

  context '#call' do
    context 'when valid request' do
      it 'returns an auth token' do
        auth_token = valid_request.call
        expect(JsonWebToken.decode(auth_token)[:user_id]).to eq(user.id)
      end
    end
    context 'when invalid request' do
      it 'raises AuthenticationError' do
        expect { invalid_request.call }.to raise_error(ExceptionHelper::AuthenticationError, /Invalid credentials/)
      end
    end
  end
end