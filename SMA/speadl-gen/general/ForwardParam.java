package general;

import java.util.List;
import trace.Action;

@SuppressWarnings("all")
public abstract class ForwardParam<I> {
  public interface Requires<I> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I i();
  }
  
  public interface Component<I> extends ForwardParam.Provides<I> {
  }
  
  public interface Provides<I> {
  }
  
  public interface Parts<I> {
  }
  
  public static class ComponentImpl<I> implements ForwardParam.Component<I>, ForwardParam.Parts<I> {
    private final ForwardParam.Requires<I> bridge;
    
    private final ForwardParam<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final ForwardParam<I> implem, final ForwardParam.Requires<I> b, final boolean doInits) {
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
  }
  
  public static abstract class Agent<I> {
    public interface Requires<I> {
    }
    
    public interface Component<I> extends ForwardParam.Agent.Provides<I> {
    }
    
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public I a();
    }
    
    public interface Parts<I> {
    }
    
    public static class ComponentImpl<I> implements ForwardParam.Agent.Component<I>, ForwardParam.Agent.Parts<I> {
      private final ForwardParam.Agent.Requires<I> bridge;
      
      private final ForwardParam.Agent<I> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_a() {
        assert this.a == null: "This is a bug.";
        this.a = this.implementation.make_a();
        if (this.a == null) {
        	throw new RuntimeException("make_a() in general.ForwardParam$Agent<I> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_a();
      }
      
      public ComponentImpl(final ForwardParam.Agent<I> implem, final ForwardParam.Agent.Requires<I> b, final boolean doInits) {
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
      
      private I a;
      
      public I a() {
        return this.a;
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
    
    private ForwardParam.Agent.ComponentImpl<I> selfComponent;
    
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
    protected ForwardParam.Agent.Provides<I> provides() {
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
    protected abstract I make_a();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected ForwardParam.Agent.Requires<I> requires() {
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
    protected ForwardParam.Agent.Parts<I> parts() {
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
    public synchronized ForwardParam.Agent.Component<I> _newComponent(final ForwardParam.Agent.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
      }
      this.init = true;
      ForwardParam.Agent.ComponentImpl<I>  _comp = new ForwardParam.Agent.ComponentImpl<I>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private ForwardParam.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ForwardParam.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ForwardParam.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ForwardParam.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
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
  
  private ForwardParam.ComponentImpl<I> selfComponent;
  
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
  protected ForwardParam.Provides<I> provides() {
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
  protected ForwardParam.Requires<I> requires() {
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
  protected ForwardParam.Parts<I> parts() {
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
  public synchronized ForwardParam.Component<I> _newComponent(final ForwardParam.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ForwardParam has already been used to create a component, use another one.");
    }
    this.init = true;
    ForwardParam.ComponentImpl<I>  _comp = new ForwardParam.ComponentImpl<I>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract ForwardParam.Agent<I> make_Agent(final List<Action> actions);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ForwardParam.Agent<I> _createImplementationOfAgent(final List<Action> actions) {
    ForwardParam.Agent<I> implem = make_Agent(actions);
    if (implem == null) {
    	throw new RuntimeException("make_Agent() in general.ForwardParam should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected ForwardParam.Agent.Component<I> newAgent(final List<Action> actions) {
    ForwardParam.Agent<I> _implem = _createImplementationOfAgent(actions);
    return _implem._newComponent(new ForwardParam.Agent.Requires<I>() {},true);
  }
}
