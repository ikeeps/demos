require 'rails_helper'

RSpec.describe AuthorizedRequestApi do
  let(:user) { create(:user) }
  let(:headers) { {'Authorization' => token_generator(user.id) } }
  subject(:valid_request_obj) { described_class.new(headers) }
  
  describe '#call' do
    context 'when valid request' do
      it 'returns user object' do
        result = valid_request_obj.call()
        expect(result[:user]).to eq(user)
      end
    end

    context 'when invalid request' do
      context 'when missing token' do
        subject(:invalid_request_obj) { described_class.new({}) }
        it 'raises MissingToken Error' do
          expect { invalid_request_obj.call }.to raise_error(ExceptionHelper::MissingToken, 'Missing token')
        end
      end

      context 'when invalid token' do
        subject(:invalid_request_obj) do
          described_class.new( { 'Authorization' => token_generator(100) } )
        end
        it 'raises InvalidToken error' do
          expect { invalid_request_obj.call }.to raise_error(ExceptionHelper::InvalidToken, /Invalid token/)
        end
      end

      context 'when token expired' do
        
        subject(:invalid_request_obj) do
          described_class.new( { 'Authorization' => expired_token_generator(user.id) } )
        end
        it 'raises InvalidToken error' do
          expect { invalid_request_obj.call }.to raise_error(ExceptionHelper::InvalidToken, /Signature has expired/);
        end
      end

      context 'when fake token' do
        subject(:invalid_request_obj) do
          described_class.new( { 'Authorization' => 'foo' } )
        end

        it 'raises InvalidToken error' do
          expect { invalid_request_obj.call }.to raise_error(ExceptionHelper::InvalidToken, /Not enough or too many segments/)
        end
      end
    end
  end

end