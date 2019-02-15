class Parent
    def say
        puts "message in parent"
    end

    def respond_to?(method_name)
        puts "respond in parent"
        super or say
    end
end

class Child < Parent
    def hello
        puts "message in child"
    end
end

parent = Child.new
puts parent.respond_to?(:to_s)
puts parent.respond_to?(:say)
puts parent.respond_to?(:hello)
puts parent.respond_to?(:no)