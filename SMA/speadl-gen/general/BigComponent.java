package general;

import general.FET;
import general.TraceElementEater;
import trace.interfaces.TraceElement;

@SuppressWarnings("all")
public abstract class BigComponent {
  public interface Requires {
  }
  
  public interface Component extends BigComponent.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public FET.Component fet();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public TraceElementEater.Component traceEater();
  }
  
  public static class ComponentImpl implements BigComponent.Component, BigComponent.Parts {
    private final BigComponent.Requires bridge;
    
    private final BigComponent implementation;
    
    public void start() {
      assert this.fet != null: "This is a bug.";
      ((FET.ComponentImpl) this.fet).start();
      assert this.traceEater != null: "This is a bug.";
      ((TraceElementEater.ComponentImpl) this.traceEater).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_fet() {
      assert this.fet == null: "This is a bug.";
      assert this.implem_fet == null: "This is a bug.";
      this.implem_fet = this.implementation.make_fet();
      if (this.implem_fet == null) {
      	throw new RuntimeException("make_fet() in general.BigComponent should not return null.");
      }
      this.fet = this.implem_fet._newComponent(new BridgeImpl_fet(), false);
      
    }
    
    private void init_traceEater() {
      assert this.traceEater == null: "This is a bug.";
      assert this.implem_traceEater == null: "This is a bug.";
      this.implem_traceEater = this.implementation.make_traceEater();
      if (this.implem_traceEater == null) {
      	throw new RuntimeException("make_traceEater() in general.BigComponent should not return null.");
      }
      this.traceEater = this.implem_traceEater._newComponent(new BridgeImpl_traceEater(), false);
      
    }
    
    protected void initParts() {
      init_fet();
      init_traceEater();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final BigComponent implem, final BigComponent.Requires b, final boolean doInits) {
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
    
    private FET.Component fet;
    
    private FET implem_fet;
    
    private final class BridgeImpl_fet implements FET.Requires {
    }
    
    public final FET.Component fet() {
      return this.fet;
    }
    
    private TraceElementEater.Component traceEater;
    
    private TraceElementEater implem_traceEater;
    
    private final class BridgeImpl_traceEater implements TraceElementEater.Requires {
      public final TraceElement traceElement() {
        return BigComponent.ComponentImpl.this.fet().elementDeTrace();
      }
    }
    
    public final TraceElementEater.Component traceEater() {
      return this.traceEater;
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
  
  private BigComponent.ComponentImpl selfComponent;
  
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
  protected BigComponent.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected BigComponent.Requires requires() {
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
  protected BigComponent.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract FET make_fet();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract TraceElementEater make_traceEater();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized BigComponent.Component _newComponent(final BigComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BigComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    BigComponent.ComponentImpl  _comp = new BigComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BigComponent.Component newComponent() {
    return this._newComponent(new BigComponent.Requires() {}, true);
  }
}
