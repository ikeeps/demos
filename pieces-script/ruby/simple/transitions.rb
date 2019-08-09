class Transitions
  @@next_event_transitions ||= []
  @@previous_states ||= [:cart]
  @@checkout_steps = {}
  
  def initialize
    
  end
  
  def self.checkout_steps
    @@checkout_steps
  end
  
  def self.checkout_steps=(value)
    @@checkout_steps = value
  end
  
  def self.next_event_transitions
    @@next_event_transitions
  end
  
  def self.next_event_transitions=(value)
    @@next_event_transitions = value
  end
  
  def self.previous_states
    @@previous_states
  end
  
  def self.previous_states=(value)
    @@previous_states = value
  end
  
  def self.go_to_state(name, options = {})
    self.checkout_steps[name] = options
    self.previous_states.each { |state| add_transition({ from: state, to: name }.merge(options)) }
    return self.previous_states << name if options[:if]

    self.previous_states = [name]
  end
  
  def self.add_transition(options)
    self.next_event_transitions << { options.delete(:from) => options.delete(:to) }.merge(options)
  end
end
Transitions.go_to_state(:address)
Transitions.go_to_state(:delivery)
Transitions.go_to_state(:payment, if: ->(order) { order.payment? || order.payment_required? })
Transitions.go_to_state(:confirm, if: ->(order) { order.confirmation_required? })
Transitions.go_to_state(:complete)
