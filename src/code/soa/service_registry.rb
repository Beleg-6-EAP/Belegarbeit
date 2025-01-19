class ServiceRegistry
  def initialize
    @services = []
  end

  def self.instance
    @instance ||= ServiceRegistry.new
  end

  def register_service(name, description, endpoint)
    @services << { name: name, description: description, endpoint: endpoint }
  end

  def find_service(name)
    @services.find { |service| service[:name] == name }
  end
end