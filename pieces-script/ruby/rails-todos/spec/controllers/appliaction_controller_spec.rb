require 'rails_helper'

RSpec.describe ApplicationController, type: :controller do
  let!(:user) { create(:user) }
  let(:valid_headers) { { 'Authorization' => token_generator(user.id) } }
  let(:invalid_headers) { { 'Authorization' => nil} }

  describe '#authorize_request' do
    context 'when with valid headers' do
      before { allow(request).to receive(:headers).and_return(valid_headers) }

      it 'set the current user' do
        expect( subject.instance_eval { authorize_request } ).to eq(user)
      end
    end

    context 'when with invalid headers' do
      before { allow(request).to receive(:headers).and_return(invalid_headers) }

      it 'raises MissingToken error' do
        expect { subject.instance_eval { authorize_request } }.to raise_error(
          ExceptionHelper::MissingToken, /Missing token/)

      end
    end
  end
end