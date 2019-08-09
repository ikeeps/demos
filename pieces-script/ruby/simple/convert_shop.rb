shop_hash = {:a => "n", :b => "b"}
shop_value = ColorMeShop::Shop.new(shop_hash[:shop])
map_keys = Hash.new { |hash, key| hash[key] = key }

mapped_keys = Shop.column_names.map{|key| map_keys[key]}

db_data = Shop.column_names.reduce({}) do |data, key|
  mapped_key = map_keys[key]
  if !mapped_key.nil? && shop_value.respond_to?(mapped_key)
    data[key] = shop_value.send(mapped_key)
  end
  data
end
puts db_data
db_shop = Shop.find_or_create_by(shop_id: shop_value.id)
db_shop.update(db_data)
db_shop.save
