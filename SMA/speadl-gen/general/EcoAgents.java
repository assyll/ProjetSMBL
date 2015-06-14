package general;

import agents.interfaces.Do;
import agents.interfaces.IGetThread;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Agent;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;
import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;
import trace.ActionTrace;

@SuppressWarnings("all")
public abstract class EcoAgents {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGetThread threads();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ICreateAgent createAgent();
  }
  
  public interface Component extends EcoAgents.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements EcoAgents.Component, EcoAgents.Parts {
    private final EcoAgents.Requires bridge;
    
    private final EcoAgents implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final EcoAgents implem, final EcoAgents.Requires b, final boolean doInits) {
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
  
  public static abstract class StateAgent {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ContextInfos getContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public EnvUpdate setContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public CycleAlert finishedCycle();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public SendMessage push();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public PullMessage pull();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ILog finishedCycleForLog();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IGraph graph();
    }
    
    public interface Component extends EcoAgents.StateAgent.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do cycle();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ICreateAgent create();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Agent.Component<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> agentComponent();
    }
    
    public static class ComponentImpl implements EcoAgents.StateAgent.Component, EcoAgents.StateAgent.Parts {
      private final EcoAgents.StateAgent.Requires bridge;
      
      private final EcoAgents.StateAgent implementation;
      
      public void start() {
        assert this.agentComponent != null: "This is a bug.";
        ((Agent.ComponentImpl<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage>) this.agentComponent).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentComponent() {
        assert this.agentComponent == null: "This is a bug.";
        assert this.implem_agentComponent == null: "This is a bug.";
        this.implem_agentComponent = this.implementation.make_agentComponent();
        if (this.implem_agentComponent == null) {
        	throw new RuntimeException("make_agentComponent() in general.EcoAgents$StateAgent should not return null.");
        }
        this.agentComponent = this.implem_agentComponent._newComponent(new BridgeImpl_agentComponent(), false);
        
      }
      
      protected void initParts() {
        init_agentComponent();
      }
      
      private void init_create() {
        assert this.create == null: "This is a bug.";
        this.create = this.implementation.make_create();
        if (this.create == null) {
        	throw new RuntimeException("make_create() in general.EcoAgents$StateAgent should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_create();
      }
      
      public ComponentImpl(final EcoAgents.StateAgent implem, final EcoAgents.StateAgent.Requires b, final boolean doInits) {
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
      
      private ICreateAgent create;
      
      public ICreateAgent create() {
        return this.create;
      }
      
      private Agent.Component<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> agentComponent;
      
      private Agent<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> implem_agentComponent;
      
      private final class BridgeImpl_agentComponent implements Agent.Requires<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> {
        public final ContextInfos getContext() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.getContext();
        }
        
        public final EnvUpdate setContext() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.setContext();
        }
        
        public final CycleAlert finishedCycle() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.finishedCycle();
        }
        
        public final SendMessage push() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.push();
        }
        
        public final PullMessage pull() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.pull();
        }
        
        public final ICreateAgent create() {
          return EcoAgents.StateAgent.ComponentImpl.this.create();
        }
        
        public final ILog finishedCycleForLog() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.finishedCycleForLog();
        }
        
        public final IGraph graph() {
          return EcoAgents.StateAgent.ComponentImpl.this.bridge.graph();
        }
      }
      
      public final Agent.Component<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> agentComponent() {
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
    
    private EcoAgents.StateAgent.ComponentImpl selfComponent;
    
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
    protected EcoAgents.StateAgent.Provides provides() {
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
    protected abstract ICreateAgent make_create();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected EcoAgents.StateAgent.Requires requires() {
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
    protected EcoAgents.StateAgent.Parts parts() {
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
    protected abstract Agent<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> make_agentComponent();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoAgents.StateAgent.Component _newComponent(final EcoAgents.StateAgent.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of StateAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgents.StateAgent.ComponentImpl  _comp = new EcoAgents.StateAgent.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgents.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgents.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgents.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgents.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static abstract class TransitionAgent {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public EnvInfos getContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public EnvUpdate setContext();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public CycleAlert finishedCycle();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public SendMessage push();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public PullMessage pull();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ILog finishedCycleForLog();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IGraph graph();
    }
    
    public interface Component extends EcoAgents.TransitionAgent.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do cycle();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ICreateAgent create();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Agent.Component<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> agentComponent();
    }
    
    public static class ComponentImpl implements EcoAgents.TransitionAgent.Component, EcoAgents.TransitionAgent.Parts {
      private final EcoAgents.TransitionAgent.Requires bridge;
      
      private final EcoAgents.TransitionAgent implementation;
      
      public void start() {
        assert this.agentComponent != null: "This is a bug.";
        ((Agent.ComponentImpl<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage>) this.agentComponent).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentComponent() {
        assert this.agentComponent == null: "This is a bug.";
        assert this.implem_agentComponent == null: "This is a bug.";
        this.implem_agentComponent = this.implementation.make_agentComponent();
        if (this.implem_agentComponent == null) {
        	throw new RuntimeException("make_agentComponent() in general.EcoAgents$TransitionAgent should not return null.");
        }
        this.agentComponent = this.implem_agentComponent._newComponent(new BridgeImpl_agentComponent(), false);
        
      }
      
      protected void initParts() {
        init_agentComponent();
      }
      
      private void init_create() {
        assert this.create == null: "This is a bug.";
        this.create = this.implementation.make_create();
        if (this.create == null) {
        	throw new RuntimeException("make_create() in general.EcoAgents$TransitionAgent should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_create();
      }
      
      public ComponentImpl(final EcoAgents.TransitionAgent implem, final EcoAgents.TransitionAgent.Requires b, final boolean doInits) {
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
      
      private ICreateAgent create;
      
      public ICreateAgent create() {
        return this.create;
      }
      
      private Agent.Component<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> agentComponent;
      
      private Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> implem_agentComponent;
      
      private final class BridgeImpl_agentComponent implements Agent.Requires<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> {
        public final EnvInfos getContext() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.getContext();
        }
        
        public final EnvUpdate setContext() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.setContext();
        }
        
        public final CycleAlert finishedCycle() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.finishedCycle();
        }
        
        public final SendMessage push() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.push();
        }
        
        public final PullMessage pull() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.pull();
        }
        
        public final ICreateAgent create() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.create();
        }
        
        public final ILog finishedCycleForLog() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.finishedCycleForLog();
        }
        
        public final IGraph graph() {
          return EcoAgents.TransitionAgent.ComponentImpl.this.bridge.graph();
        }
      }
      
      public final Agent.Component<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> agentComponent() {
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
    
    private EcoAgents.TransitionAgent.ComponentImpl selfComponent;
    
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
    protected EcoAgents.TransitionAgent.Provides provides() {
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
    protected abstract ICreateAgent make_create();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected EcoAgents.TransitionAgent.Requires requires() {
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
    protected EcoAgents.TransitionAgent.Parts parts() {
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
    protected abstract Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> make_agentComponent();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoAgents.TransitionAgent.Component _newComponent(final EcoAgents.TransitionAgent.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of TransitionAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoAgents.TransitionAgent.ComponentImpl  _comp = new EcoAgents.TransitionAgent.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoAgents.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoAgents.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoAgents.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoAgents.Parts eco_parts() {
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
  
  private EcoAgents.ComponentImpl selfComponent;
  
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
  protected EcoAgents.Provides provides() {
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
  protected EcoAgents.Requires requires() {
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
  protected EcoAgents.Parts parts() {
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
  public synchronized EcoAgents.Component _newComponent(final EcoAgents.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoAgents has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoAgents.ComponentImpl  _comp = new EcoAgents.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract EcoAgents.StateAgent make_StateAgent(final String id, final boolean isRoot);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgents.StateAgent _createImplementationOfStateAgent(final String id, final boolean isRoot) {
    EcoAgents.StateAgent implem = make_StateAgent(id,isRoot);
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
  protected abstract EcoAgents.TransitionAgent make_TransitionAgent(final String id, final ActionTrace action, final String idSource, final String idCible, final boolean createCible);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoAgents.TransitionAgent _createImplementationOfTransitionAgent(final String id, final ActionTrace action, final String idSource, final String idCible, final boolean createCible) {
    EcoAgents.TransitionAgent implem = make_TransitionAgent(id,action,idSource,idCible,createCible);
    if (implem == null) {
    	throw new RuntimeException("make_TransitionAgent() in general.EcoAgents should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
