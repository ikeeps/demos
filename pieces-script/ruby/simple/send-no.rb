class A
    def method_a
        puts "a in A"
        puts __send__ :to_s
        puts __send__ :class
        __send__ :method_b
    end

    def method_b
        puts "b in A"
    end
end

a = A.new

a.method_a