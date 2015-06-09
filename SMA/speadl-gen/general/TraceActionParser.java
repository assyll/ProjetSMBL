package general;

import trace.interfaces.TraceElement;

@SuppressWarnings("all")
public abstract class TraceActionParser {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public TraceElement traceElement();
  }
  
  public interface Component extends TraceActionParser.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public TraceElement actionTrace();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements TraceActionParser.Component, TraceActionParser.Parts {
    private final TraceActionParser.Requires bridge;
    
    private final TraceActionParser implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_actionTrace() {
      assert this.actionTrace == null: "This is a bug.";
      this.actionTrace = this.implementation.make_actionTrace();
      if (this.actionTrace == null) {
      	throw new RuntimeException("make_actionTrace() in general.TraceActionParser should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_actionTrace();
    }
    
    public ComponentImpl(final TraceActionParser implem, final TraceActionParser.Requires b, final boolean doInits) {
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
    
    private TraceElement actionTrace;
    
    public TraceElement actionTrace() {
      return this.actionTrace;
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
  
  private TraceActionParser.ComponentImpl selfComponent;
  
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
  protected TraceActionParser.Provides provides() {
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
  protected abstract TraceElement make_actionTrace();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected TraceActionParser.Requires requires() {
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
  protected TraceActionParser.Parts parts() {
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
  public synchronized TraceActionParser.Component _newComponent(final TraceActionParser.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of TraceActionParser has already been used to create a component, use another one.");
    }
    this.init = true;
    TraceActionParser.ComponentImpl  _comp = new TraceActionParser.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
