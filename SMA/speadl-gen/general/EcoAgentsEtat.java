package general;

import agents.interfaces.Do;
import agents.interfaces.IGetThread;
import general.Act;
import general.Decide;
import general.Perceive;
import generalStructure.interfaces.CycleAlert;

@SuppressWarnings("all")
public abstract class EcoAgentsEtat {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGetThread threads();
  }
  
  public interface Component extends EcoAgentsEtat.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements EcoAgentsEtat.Component, EcoAgentsEtat.Parts {
    private final EcoAgentsEtat.Requires bridge;
    
    private final EcoAgentsEtat implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final EcoAgentsEtat implem, final EcoAgentsEtat.Requires b, final boolean doInits) {
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
  
  public static abstract class AgentEtat {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public CycleAlert finishedCycle();
    }
    
    public interface Component extends EcoAgentsEtat.AgentEtat.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do cycle();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Act.Component act();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Decide.Component decide();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Perceive.Component perceive();
    }
    
    public static class ComponentImpl implements EcoAgentsEtat.AgentEtat.Component, EcoAgentsEtat.AgentEtat.Parts {
      private final EcoAgentsEtat.AgentEtat.Requires bridge;
      
      private final EcoAgentsEtat.AgentEtat implementation;
      
      public void start() {
        assert this.act != null: "This is a bug.";
        ((Act.ComponentImpl) this.act).start();
        assert this.decide != null: "This is a bug.";
        ((Decide.ComponentImpl) this.decide).start();
        assert this.perceive != null: "This is a bug.";
        ((Perceive.ComponentImpl) this.perceive).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_act() {
        assert this.act == null: "This is a bug.";
        assert this.implem_act == null: "This is a bug.";
        this.implem_act = this.implementation.make_act();
        if (this.implem_act == null) {
        	throw new RuntimeException("make_act() in general.EcoAgentsEtat$AgentEtat should not return null.");
        }
        this.act = this.implem_act._newComponent(new BridgeImpl_act(), false);
        
      }
      
      private void init_decide() {
        assert this.decide == null: "This is a bug.";
        assert this.implem_decide == null: "This is a bug.";
        this.implem_decide = this.implementation.make_decide();
        if (this.implem_decide == null) {
        	throw new RuntimeException("make_decide() in general.EcoAgentsEtat$AgentEtat should not return null.");
        }
        this.decide = this.implem_decide._newComponent(new BridgeImpl_decide(), false);
        
      }
      
      private void init_perceive() {
        assert this.perceive == null: "This is a bug.";
        assert this.implem_perceive == null: "This is a bug.";
        this.implem_perceive = this.implementation.make_perceive();
        if (this.implem_perceive == null) {
        	throw new RuntimeException("make_perceive() in general.EcoAgentsEtat$AgentEtat should not return null.");
        }
        this.perceive = this.implem_perceive._newComponent(new BridgeImpl_perceive(), false);
        
      }
      
      protected void initParts() {
        init_act();
        init_decide();
        init_perceive();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoAgentsEtat.AgentEtat implem, final EcoAgentsEtat.AgentEtat.Requires b, final boolean doInits) {
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
      
      public Do cycle() {
        return this.perceive().perception();
      }
      
      private Act.Component act;
      
      private Act implem_act;
      
      private final class BridgeImpl_act implements Act.Requires {
        public final CycleAlert finishedCycle() {
          return EcoAgentsEtat.AgentEtat.ComponentImpl.this.bridge.finishedCycle();
        }
      }
      
      public final Act.Component act() {
        return this.act;
      }
      
      private Decide.Component decide;
      
      private Decide implem_decide;
      
      private final class BridgeImpl_decide implements Decide.Requires {
        public final Do action() {
          return EcoAgentsEtat.AgentEtat.ComponentImpl.this.act().action();
        }
      }
      
      public final Decide.Component decide() {
        return this.decide;
      }
      
      private Perceive.Component perceive;
      
      private Perceive implem_perceive;
      
      private final class BridgeImpl_perceive implements Perceive.Requires {
        public final Do decision() {
          return EcoAgentsEtat.AgentEtat.ComponentImpl.this.decide().decision();
        }
      }
      
      public final Perceive.Component perceive() {
        return this.perceive;
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
    
    private EcoAgentsEtat.AgentEtat.ComponentImpl selfComponent;
    
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
    protected EcoAgentsEtat.AgentEtat.Provides provides() {
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
    protected EcoAgentsEtat.AgentEtat.Requires requires() {
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
    protected EcoAgentsEtat.AgentEtat.Parts parts() {
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
    protected abstract Act make_act();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Decide make_decide();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Perceive make_perceive();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoAgentsEtat.AgentEtat.Component _newComponent(final EcoAgentsEtat.AgentEtat.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of AgentEtat has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgentsEtat.AgentEtat.ComponentImpl  _comp = new EcoAgentsEtat.AgentEtat.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgentsEtat.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgentsEtat.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgentsEtat.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgentsEtat.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class AgentTransition {
    public interface Requires {
    }
    
    public interface Component extends EcoAgentsEtat.AgentTransition.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements EcoAgentsEtat.AgentTransition.Component, EcoAgentsEtat.AgentTransition.Parts {
      private final EcoAgentsEtat.AgentTransition.Requires bridge;
      
      private final EcoAgentsEtat.AgentTransition implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoAgentsEtat.AgentTransition implem, final EcoAgentsEtat.AgentTransition.Requires b, final boolean doInits) {
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
    
    private EcoAgentsEtat.AgentTransition.ComponentImpl selfComponent;
    
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
    protected EcoAgentsEtat.AgentTransition.Provides provides() {
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
    protected EcoAgentsEtat.AgentTransition.Requires requires() {
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
    protected EcoAgentsEtat.AgentTransition.Parts parts() {
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
    public synchronized EcoAgentsEtat.AgentTransition.Component _newComponent(final EcoAgentsEtat.AgentTransition.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of AgentTransition has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgentsEtat.AgentTransition.ComponentImpl  _comp = new EcoAgentsEtat.AgentTransition.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgentsEtat.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgentsEtat.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgentsEtat.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgentsEtat.Parts eco_parts() {
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
  
  private EcoAgentsEtat.ComponentImpl selfComponent;
  
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
  protected EcoAgentsEtat.Provides provides() {
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
  protected EcoAgentsEtat.Requires requires() {
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
  protected EcoAgentsEtat.Parts parts() {
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
  public synchronized EcoAgentsEtat.Component _newComponent(final EcoAgentsEtat.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoAgentsEtat has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoAgentsEtat.ComponentImpl  _comp = new EcoAgentsEtat.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract EcoAgentsEtat.AgentEtat make_AgentEtat(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgentsEtat.AgentEtat _createImplementationOfAgentEtat(final String id) {
    EcoAgentsEtat.AgentEtat implem = make_AgentEtat(id);
    if (implem == null) {
    	throw new RuntimeException("make_AgentEtat() in general.EcoAgentsEtat should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected EcoAgentsEtat.AgentTransition make_AgentTransition(final String id) {
    return new EcoAgentsEtat.AgentTransition();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgentsEtat.AgentTransition _createImplementationOfAgentTransition(final String id) {
    EcoAgentsEtat.AgentTransition implem = make_AgentTransition(id);
    if (implem == null) {
    	throw new RuntimeException("make_AgentTransition() in general.EcoAgentsEtat should not return null.");
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
  protected EcoAgentsEtat.AgentTransition.Component newAgentTransition(final String id) {
    EcoAgentsEtat.AgentTransition _implem = _createImplementationOfAgentTransition(id);
    return _implem._newComponent(new EcoAgentsEtat.AgentTransition.Requires() {},true);
  }
}
