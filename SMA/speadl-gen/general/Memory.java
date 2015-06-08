package general;

@SuppressWarnings("all")
public abstract class Memory<SharedMemory> {
  public interface Requires<SharedMemory> {
  }
  
  public interface Component<SharedMemory> extends Memory.Provides<SharedMemory> {
  }
  
  public interface Provides<SharedMemory> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public SharedMemory infos();
  }
  
  public interface Parts<SharedMemory> {
  }
  
  public static class ComponentImpl<SharedMemory> implements Memory.Component<SharedMemory>, Memory.Parts<SharedMemory> {
    private final Memory.Requires<SharedMemory> bridge;
    
    private final Memory<SharedMemory> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_infos() {
      assert this.infos == null: "This is a bug.";
      this.infos = this.implementation.make_infos();
      if (this.infos == null) {
      	throw new RuntimeException("make_infos() in general.Memory<SharedMemory> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_infos();
    }
    
    public ComponentImpl(final Memory<SharedMemory> implem, final Memory.Requires<SharedMemory> b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private SharedMemory infos;
    
    public SharedMemory infos() {
      return this.infos;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private Memory.ComponentImpl<SharedMemory> selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected Memory.Provides<SharedMemory> provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract SharedMemory make_infos();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Memory.Requires<SharedMemory> requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected Memory.Parts<SharedMemory> parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Memory.Component<SharedMemory> _newComponent(final Memory.Requires<SharedMemory> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Memory has already been used to create a component, use another one.");
    }
    this.init = true;
    Memory.ComponentImpl<SharedMemory>  _comp = new Memory.ComponentImpl<SharedMemory>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Memory.Component<SharedMemory> newComponent() {
    return this._newComponent(new Memory.Requires<SharedMemory>() {}, true);
  }
}
