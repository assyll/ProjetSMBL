package general;

import agents.interfaces.Do;
import agents.interfaces.IGetThread;
import general.Agent;
import generalStructure.interfaces.CycleAlert;

@SuppressWarnings("all")
public abstract class EcoAgents<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGetThread threads();
  }
  
  public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends EcoAgents.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements EcoAgents.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, EcoAgents.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    private final EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
    
    private final EcoAgents<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final EcoAgents<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
  
  public static abstract class StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Context getContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ContextUpdate setContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public CycleAlert finishedCycle();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push push();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull pull();
    }
    
    public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends EcoAgents.StateAgent.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do cycle();
    }
    
    public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Agent.Component<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> agentComponent();
    }
    
    public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements EcoAgents.StateAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, EcoAgents.StateAgent.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      private final EcoAgents.StateAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
      
      private final EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
      
      public void start() {
        assert this.agentComponent != null: "This is a bug.";
        ((Agent.ComponentImpl<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull>) this.agentComponent).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentComponent() {
        assert this.agentComponent == null: "This is a bug.";
        assert this.implem_agentComponent == null: "This is a bug.";
        this.implem_agentComponent = this.implementation.make_agentComponent();
        if (this.implem_agentComponent == null) {
        	throw new RuntimeException("make_agentComponent() in general.EcoAgents$StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
        }
        this.agentComponent = this.implem_agentComponent._newComponent(new BridgeImpl_agentComponent(), false);
        
      }
      
      protected void initParts() {
        init_agentComponent();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final EcoAgents.StateAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
        return this.agentComponent().cycle();
      }
      
      private Agent.Component<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> agentComponent;
      
      private Agent<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> implem_agentComponent;
      
      private final class BridgeImpl_agentComponent implements Agent.Requires<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> {
        public final Context getContext() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.getContext();
        }
        
        public final ContextUpdate setContext() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.setContext();
        }
        
        public final CycleAlert finishedCycle() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.finishedCycle();
        }
        
        public final Push push() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.push();
        }
        
        public final Pull pull() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.pull();
        }
      }
      
      public final Agent.Component<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> agentComponent() {
        return this.agentComponent;
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
    
    private EcoAgents.StateAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
    
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
    protected EcoAgents.StateAgent.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
    protected EcoAgents.StateAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
    protected EcoAgents.StateAgent.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
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
    protected abstract Agent<Context, ContextUpdate, ActionableState, StateSharedMemory, Push, Pull> make_agentComponent();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoAgents.StateAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final EcoAgents.StateAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of StateAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgents.StateAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new EcoAgents.StateAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgents.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgents.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static abstract class TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Context getContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ContextUpdate setContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public CycleAlert finishedCycle();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push push();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull pull();
    }
    
    public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends EcoAgents.TransitionAgent.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do cycle();
    }
    
    public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Agent.Component<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> agentComponent();
    }
    
    public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements EcoAgents.TransitionAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, EcoAgents.TransitionAgent.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      private final EcoAgents.TransitionAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
      
      private final EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
      
      public void start() {
        assert this.agentComponent != null: "This is a bug.";
        ((Agent.ComponentImpl<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull>) this.agentComponent).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentComponent() {
        assert this.agentComponent == null: "This is a bug.";
        assert this.implem_agentComponent == null: "This is a bug.";
        this.implem_agentComponent = this.implementation.make_agentComponent();
        if (this.implem_agentComponent == null) {
        	throw new RuntimeException("make_agentComponent() in general.EcoAgents$TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
        }
        this.agentComponent = this.implem_agentComponent._newComponent(new BridgeImpl_agentComponent(), false);
        
      }
      
      protected void initParts() {
        init_agentComponent();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final EcoAgents.TransitionAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
        return this.agentComponent().cycle();
      }
      
      private Agent.Component<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> agentComponent;
      
      private Agent<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> implem_agentComponent;
      
      private final class BridgeImpl_agentComponent implements Agent.Requires<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> {
        public final Context getContext() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.getContext();
        }
        
        public final ContextUpdate setContext() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.setContext();
        }
        
        public final CycleAlert finishedCycle() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.finishedCycle();
        }
        
        public final Push push() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.push();
        }
        
        public final Pull pull() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.pull();
        }
      }
      
      public final Agent.Component<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> agentComponent() {
        return this.agentComponent;
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
    
    private EcoAgents.TransitionAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
    
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
    protected EcoAgents.TransitionAgent.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
    protected EcoAgents.TransitionAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
    protected EcoAgents.TransitionAgent.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
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
    protected abstract Agent<Context, ContextUpdate, ActionableTransition, TransSharedMemory, Push, Pull> make_agentComponent();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoAgents.TransitionAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final EcoAgents.TransitionAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of TransitionAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgents.TransitionAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new EcoAgents.TransitionAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgents.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgents.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_parts() {
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
  
  private EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
  
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
  protected EcoAgents.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
  protected EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
  protected EcoAgents.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
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
  public synchronized EcoAgents.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoAgents has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> make_StateAgent(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _createImplementationOfStateAgent(final String id) {
    EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem = make_StateAgent(id);
    if (implem == null) {
    	throw new RuntimeException("make_StateAgent() in general.EcoAgents should not return null.");
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
  protected abstract EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> make_TransitionAgent(final String id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _createImplementationOfTransitionAgent(final String id) {
    EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem = make_TransitionAgent(id);
    if (implem == null) {
    	throw new RuntimeException("make_TransitionAgent() in general.EcoAgents should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
