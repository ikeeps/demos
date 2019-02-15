

if __FILE__ == $0
    a = 1
    b = 2
    c = 'lorem ipsum'
    print <<-TEXT unless a != 1
    #{ b == 2 ? c : :blue }
    TEXT
    print <<TEXT unless a != 1
    #{ b != 2 ? c : :blue }
TEXT
end