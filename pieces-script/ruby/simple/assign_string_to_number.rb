table_id = 123456
api_id = "P09283"
table_id = api_id
puts table_id

class Sync
  def initialize(table_id)
    @table_id = table_id
  end
  attr_accessor :table_id
end

sync = Sync.new(123456)
sync.table_id = api_id
puts sync.table_id

