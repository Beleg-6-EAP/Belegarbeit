class ServiceRegistry
  def initialize
    @services = []
  end

  def self.instance
    @instance ||= ServiceRegistry.new
  end

  def register_service(name, wsdl_url)
    @services << { name: name, wsdl_url: wsdl_url }
  end

  def find_service(name)
    @services.find { |service| service[:name] == name }
  end
end