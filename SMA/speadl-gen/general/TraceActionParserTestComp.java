package general;

import general.FET;
import general.TraceActionParser;
import trace.interfaces.TraceElement;

@SuppressWarnings("all")
public abstract class TraceActionParserTestComp {
  public interface Requires {
  }
  
  public interface Component extends TraceActionParserTestComp.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public TraceElement actionTrace();
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
    public TraceActionParser.Component tap();
  }
  
  public static class ComponentImpl implements TraceActionParserTestComp.Component, TraceActionParserTestComp.Parts {
    private final TraceActionParserTestComp.Requires bridge;
    
    private final TraceActionParserTestComp implementation;
    
    public void start() {
      assert this.fet != null: "This is a bug.";
      ((FET.ComponentImpl) this.fet).start();
      assert this.tap != null: "This is a bug.";
      ((TraceActionParser.ComponentImpl) this.tap).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_fet() {
      assert this.fet == null: "This is a bug.";
      assert this.implem_fet == null: "This is a bug.";
      this.implem_fet = this.implementation.make_fet();
      if (this.implem_fet == null) {
      	throw new RuntimeException("make_fet() in general.TraceActionParserTestComp should not return null.");
      }
      this.fet = this.implem_fet._newComponent(new BridgeImpl_fet(), false);
      
    }
    
    private void init_tap() {
      assert this.tap == null: "This is a bug.";
      assert this.implem_tap == null: "This is a bug.";
      this.implem_tap = this.implementation.make_tap();
      if (this.implem_tap == null) {
      	throw new RuntimeException("make_tap() in general.TraceActionParserTestComp should not return null.");
      }
      this.tap = this.implem_tap._newComponent(new BridgeImpl_tap(), false);
      
    }
    
    protected void initParts() {
      init_fet();
      init_tap();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final TraceActionParserTestComp implem, final TraceActionParserTestComp.Requires b, final boolean doInits) {
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
    
    public TraceElement actionTrace() {
      return this.tap().actionTrace();
    }
    
    private FET.Component fet;
    
    private FET implem_fet;
    
    private final class BridgeImpl_fet implements FET.Requires {
    }
    
    public final FET.Component fet() {
      return this.fet;
    }
    
    private TraceActionParser.Component tap;
    
    private TraceActionParser implem_tap;
    
    private final class BridgeImpl_tap implements TraceActionParser.Requires {
      public final TraceElement traceElement() {
        return TraceActionParserTestComp.ComponentImpl.this.fet().traceElement();
      }
    }
    
    public final TraceActionParser.Component tap() {
      return this.tap;
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
  
  private TraceActionParserTestComp.ComponentImpl selfComponent;
  
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
  protected TraceActionParserTestComp.Provides provides() {
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
  protected TraceActionParserTestComp.Requires requires() {
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
  protected TraceActionParserTestComp.Parts parts() {
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
  protected abstract TraceActionParser make_tap();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized TraceActionParserTestComp.Component _newComponent(final TraceActionParserTestComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of TraceActionParserTestComp has already been used to create a component, use another one.");
    }
    this.init = true;
    TraceActionParserTestComp.ComponentImpl  _comp = new TraceActionParserTestComp.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public TraceActionParserTestComp.Component newComponent() {
    return this._newComponent(new TraceActionParserTestComp.Requires() {}, true);
  }
}
