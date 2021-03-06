package general;

import agents.interfaces.Do;

@SuppressWarnings("all")
public abstract class Decide<Actionable, SharedMemory> {
  public interface Requires<Actionable, SharedMemory> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Actionable action();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public SharedMemory memory();
  }
  
  public interface Component<Actionable, SharedMemory> extends Decide.Provides<Actionable, SharedMemory> {
  }
  
  public interface Provides<Actionable, SharedMemory> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do decision();
  }
  
  public interface Parts<Actionable, SharedMemory> {
  }
  
  public static class ComponentImpl<Actionable, SharedMemory> implements Decide.Component<Actionable, SharedMemory>, Decide.Parts<Actionable, SharedMemory> {
    private final Decide.Requires<Actionable, SharedMemory> bridge;
    
    private final Decide<Actionable, SharedMemory> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_decision() {
      assert this.decision == null: "This is a bug.";
      this.decision = this.implementation.make_decision();
      if (this.decision == null) {
      	throw new RuntimeException("make_decision() in general.Decide<Actionable, SharedMemory> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_decision();
    }
    
    public ComponentImpl(final Decide<Actionable, SharedMemory> implem, final Decide.Requires<Actionable, SharedMemory> b, final boolean doInits) {
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
    
    private Do decision;
    
    public Do decision() {
      return this.decision;
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
  
  private Decide.ComponentImpl<Actionable, SharedMemory> selfComponent;
  
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
  protected Decide.Provides<Actionable, SharedMemory> provides() {
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
  protected abstract Do make_decision();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Decide.Requires<Actionable, SharedMemory> requires() {
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
  protected Decide.Parts<Actionable, SharedMemory> parts() {
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
  public synchronized Decide.Component<Actionable, SharedMemory> _newComponent(final Decide.Requires<Actionable, SharedMemory> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Decide has already been used to create a component, use another one.");
    }
    this.init = true;
    Decide.ComponentImpl<Actionable, SharedMemory>  _comp = new Decide.ComponentImpl<Actionable, SharedMemory>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
