package general;

import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ILog;

@SuppressWarnings("all")
public abstract class Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
  public interface Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CycleAlert finishedCycle();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ContextUpdate setContext();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public SharedMemory memory();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateAgent create();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ILog finishedCycleForLog();
  }
  
  public interface Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> extends Act.Provides<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
  }
  
  public interface Provides<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Actionable action();
  }
  
  public interface Parts<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
  }
  
  public static class ComponentImpl<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> implements Act.Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push>, Act.Parts<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {
    private final Act.Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> bridge;
    
    private final Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_action() {
      assert this.action == null: "This is a bug.";
      this.action = this.implementation.make_action();
      if (this.action == null) {
      	throw new RuntimeException("make_action() in general.Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_action();
    }
    
    public ComponentImpl(final Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> implem, final Act.Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> b, final boolean doInits) {
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
    
    private Actionable action;
    
    public Actionable action() {
      return this.action;
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
  
  private Act.ComponentImpl<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> selfComponent;
  
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
  protected Act.Provides<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> provides() {
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
  protected abstract Actionable make_action();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Act.Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> requires() {
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
  protected Act.Parts<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> parts() {
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
  public synchronized Act.Component<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> _newComponent(final Act.Requires<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Act has already been used to create a component, use another one.");
    }
    this.init = true;
    Act.ComponentImpl<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push>  _comp = new Act.ComponentImpl<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
