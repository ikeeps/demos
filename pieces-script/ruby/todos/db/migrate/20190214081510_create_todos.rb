class CreateTodos < ActiveRecord::Migration[5.2]
  def change
    create_table :todos do |t|
      t.string :subject
      t.string :aasm_state
      t.integer :position

      t.timestamps
    end
  end
end
