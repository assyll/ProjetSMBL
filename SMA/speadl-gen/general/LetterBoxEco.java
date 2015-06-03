package general;

import trace.interfaces.IAddAction;
import trace.interfaces.ITakeAction;
import trace.interfaces.IUpdateCurrentAgent;

@SuppressWarnings("all")
public abstract class LetterBoxEco {
  public interface Requires {
  }
  
  public interface Component extends LetterBoxEco.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IAddAction addAction();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IUpdateCurrentAgent updateCurrentAgent();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements LetterBoxEco.Component, LetterBoxEco.Parts {
    private final LetterBoxEco.Requires bridge;
    
    private final LetterBoxEco implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_addAction() {
      assert this.addAction == null: "This is a bug.";
      this.addAction = this.implementation.make_addAction();
      if (this.addAction == null) {
      	throw new RuntimeException("make_addAction() in general.LetterBoxEco should not return null.");
      }
    }
    
    private void init_updateCurrentAgent() {
      assert this.updateCurrentAgent == null: "This is a bug.";
      this.updateCurrentAgent = this.implementation.make_updateCurrentAgent();
      if (this.updateCurrentAgent == null) {
      	throw new RuntimeException("make_updateCurrentAgent() in general.LetterBoxEco should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_addAction();
      init_updateCurrentAgent();
    }
    
    public ComponentImpl(final LetterBoxEco implem, final LetterBoxEco.Requires b, final boolean doInits) {
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
    
    private IAddAction addAction;
    
    public IAddAction addAction() {
      return this.addAction;
    }
    
    private IUpdateCurrentAgent updateCurrentAgent;
    
    public IUpdateCurrentAgent updateCurrentAgent() {
      return this.updateCurrentAgent;
    }
  }
  
  public static abstract class LetterBoxSpecies {
    public interface Requires {
    }
    
    public interface Component extends LetterBoxEco.LetterBoxSpecies.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ITakeAction getAction();
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements LetterBoxEco.LetterBoxSpecies.Component, LetterBoxEco.LetterBoxSpecies.Parts {
      private final LetterBoxEco.LetterBoxSpecies.Requires bridge;
      
      private final LetterBoxEco.LetterBoxSpecies implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_getAction() {
        assert this.getAction == null: "This is a bug.";
        this.getAction = this.implementation.make_getAction();
        if (this.getAction == null) {
        	throw new RuntimeException("make_getAction() in general.LetterBoxEco$LetterBoxSpecies should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_getAction();
      }
      
      public ComponentImpl(final LetterBoxEco.LetterBoxSpecies implem, final LetterBoxEco.LetterBoxSpecies.Requires b, final boolean doInits) {
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
      
      private ITakeAction getAction;
      
      public ITakeAction getAction() {
        return this.getAction;
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
    
    private LetterBoxEco.LetterBoxSpecies.ComponentImpl selfComponent;
    
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
    protected LetterBoxEco.LetterBoxSpecies.Provides provides() {
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
    protected abstract ITakeAction make_getAction();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected LetterBoxEco.LetterBoxSpecies.Requires requires() {
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
    protected LetterBoxEco.LetterBoxSpecies.Parts parts() {
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
    public synchronized LetterBoxEco.LetterBoxSpecies.Component _newComponent(final LetterBoxEco.LetterBoxSpecies.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of LetterBoxSpecies has already been used to create a component, use another one.");
      }
      this.init = true;
      LetterBoxEco.LetterBoxSpecies.ComponentImpl  _comp = new LetterBoxEco.LetterBoxSpecies.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private LetterBoxEco.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected LetterBoxEco.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected LetterBoxEco.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected LetterBoxEco.Parts eco_parts() {
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
  
  private LetterBoxEco.ComponentImpl selfComponent;
  
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
  protected LetterBoxEco.Provides provides() {
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
  protected abstract IAddAction make_addAction();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IUpdateCurrentAgent make_updateCurrentAgent();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected LetterBoxEco.Requires requires() {
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
  protected LetterBoxEco.Parts parts() {
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
  public synchronized LetterBoxEco.Component _newComponent(final LetterBoxEco.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of LetterBoxEco has already been used to create a component, use another one.");
    }
    this.init = true;
    LetterBoxEco.ComponentImpl  _comp = new LetterBoxEco.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract LetterBoxEco.LetterBoxSpecies make_LetterBoxSpecies();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public LetterBoxEco.LetterBoxSpecies _createImplementationOfLetterBoxSpecies() {
    LetterBoxEco.LetterBoxSpecies implem = make_LetterBoxSpecies();
    if (implem == null) {
    	throw new RuntimeException("make_LetterBoxSpecies() in general.LetterBoxEco should not return null.");
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
  protected LetterBoxEco.LetterBoxSpecies.Component newLetterBoxSpecies() {
    LetterBoxEco.LetterBoxSpecies _implem = _createImplementationOfLetterBoxSpecies();
    return _implem._newComponent(new LetterBoxEco.LetterBoxSpecies.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public LetterBoxEco.Component newComponent() {
    return this._newComponent(new LetterBoxEco.Requires() {}, true);
  }
}
