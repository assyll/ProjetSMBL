package general;

import generalStructure.interfaces.IInit;
import generalStructure.interfaces.IPath;
import trace.interfaces.TraceElement;

@SuppressWarnings("all")
public abstract class FET {
  public interface Requires {
  }
  
  public interface Component extends FET.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public TraceElement traceElement();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IInit init();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IPath path();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements FET.Component, FET.Parts {
    private final FET.Requires bridge;
    
    private final FET implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_traceElement() {
      assert this.traceElement == null: "This is a bug.";
      this.traceElement = this.implementation.make_traceElement();
      if (this.traceElement == null) {
      	throw new RuntimeException("make_traceElement() in general.FET should not return null.");
      }
    }
    
    private void init_init() {
      assert this.init == null: "This is a bug.";
      this.init = this.implementation.make_init();
      if (this.init == null) {
      	throw new RuntimeException("make_init() in general.FET should not return null.");
      }
    }
    
    private void init_path() {
      assert this.path == null: "This is a bug.";
      this.path = this.implementation.make_path();
      if (this.path == null) {
      	throw new RuntimeException("make_path() in general.FET should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_traceElement();
      init_init();
      init_path();
    }
    
    public ComponentImpl(final FET implem, final FET.Requires b, final boolean doInits) {
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
    
    private TraceElement traceElement;
    
    public TraceElement traceElement() {
      return this.traceElement;
    }
    
    private IInit init;
    
    public IInit init() {
      return this.init;
    }
    
    private IPath path;
    
    public IPath path() {
      return this.path;
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
  
  private FET.ComponentImpl selfComponent;
  
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
  protected FET.Provides provides() {
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
  protected abstract TraceElement make_traceElement();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IInit make_init();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IPath make_path();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected FET.Requires requires() {
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
  protected FET.Parts parts() {
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
  public synchronized FET.Component _newComponent(final FET.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of FET has already been used to create a component, use another one.");
    }
    this.init = true;
    FET.ComponentImpl  _comp = new FET.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public FET.Component newComponent() {
    return this._newComponent(new FET.Requires() {}, true);
  }
}
