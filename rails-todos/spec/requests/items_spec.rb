require 'rails_helper'

RSpec.describe 'Items API', type: :request do
  let!(:todo) { create(:todo) }
  let(:todo_id) { todo.id }
  let!(:items) { create_list(:item, 10, todo_id: todo_id) }
  let(:item_id) { items.first.id }

  let(:headers) { {} }
  
  describe 'GET /todos/:todo_id/items' do
    before { get "/todos/#{todo_id}/items" , params: {}, headers: headers}
    
    it 'returns items' do
      expect(json).not_to be_empty
      expect(json.size).to eq(10)
    end
    
    it 'returns status code 200' do
      expect(response).to have_http_status(200)
    end
  end

  describe 'GET /todos/:todo_id/items/:id' do
    before { get "/todos/#{todo_id}/items/#{item_id}", params: {}, headers: headers }
    
    context 'when the record exists' do
      it 'returns the item' do
        expect(json).not_to be_empty
        expect(json['id']).to eq(item_id)
      end
     
      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end
    end

    context 'when the record not exists' do
      let(:item_id) { 100 }
      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
      it 'returns a not found message' do
        expect(response.body).to match(/Couldn't find Item/)
      end
    end
  end

  describe 'POST /todos/:todo_id/items' do
    let(:valid_attributes) { { name: 'Learn Elm', done: false } }
    context 'when the request is valid' do
      before { post "/todos/#{todo_id}/items", params: valid_attributes, headers: headers }
      it 'creates a item' do
        expect(json['name']).to eq('Learn Elm')
      end
      it 'return status code 201' do
        expect(response).to have_http_status(201)
      end
    end
    context 'when the request is invalid' do
      before { post "/todos/#{todo_id}/items", params: { name: nil }, headers: headers }
      it 'return status code 422' do
        expect(response).to have_http_status(422)
      end
      it 'returns validation failure message' do
        expect(response.body).to match(/Validation failed: Name can't be blank/)
      end
    end
  end

  describe 'PUT /todos/:todo_id/items/:id' do
    let(:valid_attributes) { { name: 'shopping', done: true } }
    context 'when the request is valid' do
      before { put "/todos/#{todo_id}/items/#{item_id}", params: valid_attributes, headers: headers }
      it 'updates the record' do
        expect(response.body).to be_empty
      end
      it 'returns status code 204' do
        expect(response).to have_http_status(204)
      end
    end
  end
  
  describe 'DELETE /todos/:todo_id/items/:id' do
    before { delete "/todos/#{todo_id}/items/#{item_id}", params: {}, headers: headers }
    it 'returns status code 204' do
      expect(response).to have_http_status(204)
    end
  end

end