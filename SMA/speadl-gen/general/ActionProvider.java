package general;

import general.FET;
import general.TraceActionParser;
import general.TraceElementEater;
import generalStructure.interfaces.IInit;
import trace.interfaces.ITakeAction;
import trace.interfaces.TraceElement;

@SuppressWarnings("all")
public abstract class ActionProvider {
  public interface Requires {
  }
  
  public interface Component extends ActionProvider.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IInit init();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public ITakeAction actionGetter();
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
    public TraceActionParser.Component traceParser();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public TraceElementEater.Component tee();
  }
  
  public static class ComponentImpl implements ActionProvider.Component, ActionProvider.Parts {
    private final ActionProvider.Requires bridge;
    
    private final ActionProvider implementation;
    
    public void start() {
      assert this.fet != null: "This is a bug.";
      ((FET.ComponentImpl) this.fet).start();
      assert this.traceParser != null: "This is a bug.";
      ((TraceActionParser.ComponentImpl) this.traceParser).start();
      assert this.tee != null: "This is a bug.";
      ((TraceElementEater.ComponentImpl) this.tee).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_fet() {
      assert this.fet == null: "This is a bug.";
      assert this.implem_fet == null: "This is a bug.";
      this.implem_fet = this.implementation.make_fet();
      if (this.implem_fet == null) {
      	throw new RuntimeException("make_fet() in general.ActionProvider should not return null.");
      }
      this.fet = this.implem_fet._newComponent(new BridgeImpl_fet(), false);
      
    }
    
    private void init_traceParser() {
      assert this.traceParser == null: "This is a bug.";
      assert this.implem_traceParser == null: "This is a bug.";
      this.implem_traceParser = this.implementation.make_traceParser();
      if (this.implem_traceParser == null) {
      	throw new RuntimeException("make_traceParser() in general.ActionProvider should not return null.");
      }
      this.traceParser = this.implem_traceParser._newComponent(new BridgeImpl_traceParser(), false);
      
    }
    
    private void init_tee() {
      assert this.tee == null: "This is a bug.";
      assert this.implem_tee == null: "This is a bug.";
      this.implem_tee = this.implementation.make_tee();
      if (this.implem_tee == null) {
      	throw new RuntimeException("make_tee() in general.ActionProvider should not return null.");
      }
      this.tee = this.implem_tee._newComponent(new BridgeImpl_tee(), false);
      
    }
    
    protected void initParts() {
      init_fet();
      init_traceParser();
      init_tee();
    }
    
    private void init_init() {
      assert this.init == null: "This is a bug.";
      this.init = this.implementation.make_init();
      if (this.init == null) {
      	throw new RuntimeException("make_init() in general.ActionProvider should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_init();
    }
    
    public ComponentImpl(final ActionProvider implem, final ActionProvider.Requires b, final boolean doInits) {
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
    
    private IInit init;
    
    public IInit init() {
      return this.init;
    }
    
    public ITakeAction actionGetter() {
      return this.tee().actionGetter();
    }
    
    private FET.Component fet;
    
    private FET implem_fet;
    
    private final class BridgeImpl_fet implements FET.Requires {
    }
    
    public final FET.Component fet() {
      return this.fet;
    }
    
    private TraceActionParser.Component traceParser;
    
    private TraceActionParser implem_traceParser;
    
    private final class BridgeImpl_traceParser implements TraceActionParser.Requires {
      public final TraceElement traceElement() {
        return ActionProvider.ComponentImpl.this.fet().traceElement();
      }
    }
    
    public final TraceActionParser.Component traceParser() {
      return this.traceParser;
    }
    
    private TraceElementEater.Component tee;
    
    private TraceElementEater implem_tee;
    
    private final class BridgeImpl_tee implements TraceElementEater.Requires {
      public final TraceElement traceElement() {
        return ActionProvider.ComponentImpl.this.traceParser().actionTrace();
      }
    }
    
    public final TraceElementEater.Component tee() {
      return this.tee;
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
  
  private ActionProvider.ComponentImpl selfComponent;
  
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
  protected ActionProvider.Provides provides() {
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
  protected abstract IInit make_init();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ActionProvider.Requires requires() {
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
  protected ActionProvider.Parts parts() {
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
  protected abstract TraceActionParser make_traceParser();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract TraceElementEater make_tee();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized ActionProvider.Component _newComponent(final ActionProvider.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ActionProvider has already been used to create a component, use another one.");
    }
    this.init = true;
    ActionProvider.ComponentImpl  _comp = new ActionProvider.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ActionProvider.Component newComponent() {
    return this._newComponent(new ActionProvider.Requires() {}, true);
  }
}
