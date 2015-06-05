package general;

import java.util.List;
import trace.Action;

@SuppressWarnings("all")
public abstract class Forward2<I> {
  public interface Requires<I> {
  }
  
  public interface Component<I> extends Forward2.Provides<I> {
  }
  
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public I i();
  }
  
  public interface Parts<I> {
  }
  
  public static class ComponentImpl<I> implements Forward2.Component<I>, Forward2.Parts<I> {
    private final Forward2.Requires<I> bridge;
    
    private final Forward2<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_i() {
      assert this.i == null: "This is a bug.";
      this.i = this.implementation.make_i();
      if (this.i == null) {
      	throw new RuntimeException("make_i() in general.Forward2<I> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_i();
    }
    
    public ComponentImpl(final Forward2<I> implem, final Forward2.Requires<I> b, final boolean doInits) {
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
    
    private I i;
    
    public I i() {
      return this.i;
    }
  }
  
  public static class Agent<I> {
    public interface Requires<I> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I a();
    }
    
    public interface Component<I> extends Forward2.Agent.Provides<I> {
    }
    
    public interface Provides<I> {
    }
    
    public interface Parts<I> {
    }
    
    public static class ComponentImpl<I> implements Forward2.Agent.Component<I>, Forward2.Agent.Parts<I> {
      private final Forward2.Agent.Requires<I> bridge;
      
      private final Forward2.Agent<I> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final Forward2.Agent<I> implem, final Forward2.Agent.Requires<I> b, final boolean doInits) {
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
    
    private Forward2.Agent.ComponentImpl<I> selfComponent;
    
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
    protected Forward2.Agent.Provides<I> provides() {
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
    protected Forward2.Agent.Requires<I> requires() {
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
    protected Forward2.Agent.Parts<I> parts() {
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
    public synchronized Forward2.Agent.Component<I> _newComponent(final Forward2.Agent.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward2.Agent.ComponentImpl<I>  _comp = new Forward2.Agent.ComponentImpl<I>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Forward2.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward2.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward2.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward2.Parts<I> eco_parts() {
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
  
  private Forward2.ComponentImpl<I> selfComponent;
  
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
  protected Forward2.Provides<I> provides() {
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
  protected abstract I make_i();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Forward2.Requires<I> requires() {
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
  protected Forward2.Parts<I> parts() {
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
  public synchronized Forward2.Component<I> _newComponent(final Forward2.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Forward2 has already been used to create a component, use another one.");
    }
    this.init = true;
    Forward2.ComponentImpl<I>  _comp = new Forward2.ComponentImpl<I>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected Forward2.Agent<I> make_Agent(final List<Action> actions) {
    return new Forward2.Agent<I>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward2.Agent<I> _createImplementationOfAgent(final List<Action> actions) {
    Forward2.Agent<I> implem = make_Agent(actions);
    if (implem == null) {
    	throw new RuntimeException("make_Agent() in general.Forward2 should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Forward2.Component<I> newComponent() {
    return this._newComponent(new Forward2.Requires<I>() {}, true);
  }
}
