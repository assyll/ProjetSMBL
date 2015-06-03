package general;

import agents.interfaces.IGetThread;
import general.EcoAgentsEtat;
import general.Forward;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;

@SuppressWarnings("all")
public abstract class BigEco {
  public interface Requires {
  }
  
  public interface Component extends BigEco.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EcoAgentsEtat.Component ecoAE();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward.Component<CycleAlert> fw();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Launcher.Component launcher();
  }
  
  public static class ComponentImpl implements BigEco.Component, BigEco.Parts {
    private final BigEco.Requires bridge;
    
    private final BigEco implementation;
    
    public void start() {
      assert this.ecoAE != null: "This is a bug.";
      ((EcoAgentsEtat.ComponentImpl) this.ecoAE).start();
      assert this.fw != null: "This is a bug.";
      ((Forward.ComponentImpl<CycleAlert>) this.fw).start();
      assert this.launcher != null: "This is a bug.";
      ((Launcher.ComponentImpl) this.launcher).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_ecoAE() {
      assert this.ecoAE == null: "This is a bug.";
      assert this.implem_ecoAE == null: "This is a bug.";
      this.implem_ecoAE = this.implementation.make_ecoAE();
      if (this.implem_ecoAE == null) {
      	throw new RuntimeException("make_ecoAE() in general.BigEco should not return null.");
      }
      this.ecoAE = this.implem_ecoAE._newComponent(new BridgeImpl_ecoAE(), false);
      
    }
    
    private void init_fw() {
      assert this.fw == null: "This is a bug.";
      assert this.implem_fw == null: "This is a bug.";
      this.implem_fw = this.implementation.make_fw();
      if (this.implem_fw == null) {
      	throw new RuntimeException("make_fw() in general.BigEco should not return null.");
      }
      this.fw = this.implem_fw._newComponent(new BridgeImpl_fw(), false);
      
    }
    
    private void init_launcher() {
      assert this.launcher == null: "This is a bug.";
      assert this.implem_launcher == null: "This is a bug.";
      this.implem_launcher = this.implementation.make_launcher();
      if (this.implem_launcher == null) {
      	throw new RuntimeException("make_launcher() in general.BigEco should not return null.");
      }
      this.launcher = this.implem_launcher._newComponent(new BridgeImpl_launcher(), false);
      
    }
    
    protected void initParts() {
      init_ecoAE();
      init_fw();
      init_launcher();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final BigEco implem, final BigEco.Requires b, final boolean doInits) {
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
    
    private EcoAgentsEtat.Component ecoAE;
    
    private EcoAgentsEtat implem_ecoAE;
    
    private final class BridgeImpl_ecoAE implements EcoAgentsEtat.Requires {
      public final IGetThread threads() {
        return BigEco.ComponentImpl.this.launcher().threads();
      }
    }
    
    public final EcoAgentsEtat.Component ecoAE() {
      return this.ecoAE;
    }
    
    private Forward.Component<CycleAlert> fw;
    
    private Forward<CycleAlert> implem_fw;
    
    private final class BridgeImpl_fw implements Forward.Requires<CycleAlert> {
      public final CycleAlert i() {
        return BigEco.ComponentImpl.this.launcher().finishedCycle();
      }
    }
    
    public final Forward.Component<CycleAlert> fw() {
      return this.fw;
    }
    
    private Launcher.Component launcher;
    
    private Launcher implem_launcher;
    
    private final class BridgeImpl_launcher implements Launcher.Requires {
    }
    
    public final Launcher.Component launcher() {
      return this.launcher;
    }
  }
  
  public static class DynamicAssembly {
    public interface Requires {
    }
    
    public interface Component extends BigEco.DynamicAssembly.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoAgentsEtat.AgentEtat.Component agentE();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.Agent.Component<CycleAlert> aFW();
    }
    
    public static class ComponentImpl implements BigEco.DynamicAssembly.Component, BigEco.DynamicAssembly.Parts {
      private final BigEco.DynamicAssembly.Requires bridge;
      
      private final BigEco.DynamicAssembly implementation;
      
      public void start() {
        assert this.agentE != null: "This is a bug.";
        ((EcoAgentsEtat.AgentEtat.ComponentImpl) this.agentE).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.Agent.ComponentImpl<CycleAlert>) this.aFW).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentE() {
        assert this.agentE == null: "This is a bug.";
        assert this.implementation.use_agentE != null: "This is a bug.";
        this.agentE = this.implementation.use_agentE._newComponent(new BridgeImpl_ecoAE_agentE(), false);
        
      }
      
      private void init_aFW() {
        assert this.aFW == null: "This is a bug.";
        assert this.implementation.use_aFW != null: "This is a bug.";
        this.aFW = this.implementation.use_aFW._newComponent(new BridgeImpl_fw_aFW(), false);
        
      }
      
      protected void initParts() {
        init_agentE();
        init_aFW();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final BigEco.DynamicAssembly implem, final BigEco.DynamicAssembly.Requires b, final boolean doInits) {
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
      
      private EcoAgentsEtat.AgentEtat.Component agentE;
      
      private final class BridgeImpl_ecoAE_agentE implements EcoAgentsEtat.AgentEtat.Requires {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssembly.ComponentImpl.this.aFW().a();
        }
      }
      
      public final EcoAgentsEtat.AgentEtat.Component agentE() {
        return this.agentE;
      }
      
      private Forward.Agent.Component<CycleAlert> aFW;
      
      private final class BridgeImpl_fw_aFW implements Forward.Agent.Requires<CycleAlert> {
      }
      
      public final Forward.Agent.Component<CycleAlert> aFW() {
        return this.aFW;
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
    
    private BigEco.DynamicAssembly.ComponentImpl selfComponent;
    
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
    protected BigEco.DynamicAssembly.Provides provides() {
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
    protected BigEco.DynamicAssembly.Requires requires() {
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
    protected BigEco.DynamicAssembly.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private EcoAgentsEtat.AgentEtat use_agentE;
    
    private Forward.Agent<CycleAlert> use_aFW;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssembly.Component _newComponent(final BigEco.DynamicAssembly.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssembly has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssembly.ComponentImpl  _comp = new BigEco.DynamicAssembly.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private BigEco.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected BigEco.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected BigEco.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected BigEco.Parts eco_parts() {
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
  
  private BigEco.ComponentImpl selfComponent;
  
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
  protected BigEco.Provides provides() {
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
  protected BigEco.Requires requires() {
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
  protected BigEco.Parts parts() {
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
  protected abstract EcoAgentsEtat make_ecoAE();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<CycleAlert> make_fw();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Launcher make_launcher();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized BigEco.Component _newComponent(final BigEco.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BigEco has already been used to create a component, use another one.");
    }
    this.init = true;
    BigEco.ComponentImpl  _comp = new BigEco.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BigEco.DynamicAssembly make_DynamicAssembly(final String id) {
    return new BigEco.DynamicAssembly();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssembly _createImplementationOfDynamicAssembly(final String id) {
    BigEco.DynamicAssembly implem = make_DynamicAssembly(id);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssembly() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentE == null: "This is a bug.";
    implem.use_agentE = this.selfComponent.implem_ecoAE._createImplementationOfAgentEtat(id);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfAgent();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssembly.Component newDynamicAssembly(final String id) {
    BigEco.DynamicAssembly _implem = _createImplementationOfDynamicAssembly(id);
    return _implem._newComponent(new BigEco.DynamicAssembly.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BigEco.Component newComponent() {
    return this._newComponent(new BigEco.Requires() {}, true);
  }
}
