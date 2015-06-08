package general;

import agents.interfaces.IGetThread;
import general.EcoAgents;
import general.Environnement;
import general.Forward;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;

@SuppressWarnings("all")
public abstract class BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends BigEco.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
  }
  
  public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EcoAgents.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecoAE();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> fw();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Launcher.Component launcher();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Environnement.Component<Context, ContextUpdate> envEco();
  }
  
  public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements BigEco.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, BigEco.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    private final BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
    
    private final BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
    
    public void start() {
      assert this.ecoAE != null: "This is a bug.";
      ((EcoAgents.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>) this.ecoAE).start();
      assert this.fw != null: "This is a bug.";
      ((Forward.ComponentImpl<CycleAlert, Context, ContextUpdate, Push, Pull>) this.fw).start();
      assert this.launcher != null: "This is a bug.";
      ((Launcher.ComponentImpl) this.launcher).start();
      assert this.envEco != null: "This is a bug.";
      ((Environnement.ComponentImpl<Context, ContextUpdate>) this.envEco).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_ecoAE() {
      assert this.ecoAE == null: "This is a bug.";
      assert this.implem_ecoAE == null: "This is a bug.";
      this.implem_ecoAE = this.implementation.make_ecoAE();
      if (this.implem_ecoAE == null) {
      	throw new RuntimeException("make_ecoAE() in general.BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
      }
      this.ecoAE = this.implem_ecoAE._newComponent(new BridgeImpl_ecoAE(), false);
      
    }
    
    private void init_fw() {
      assert this.fw == null: "This is a bug.";
      assert this.implem_fw == null: "This is a bug.";
      this.implem_fw = this.implementation.make_fw();
      if (this.implem_fw == null) {
      	throw new RuntimeException("make_fw() in general.BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
      }
      this.fw = this.implem_fw._newComponent(new BridgeImpl_fw(), false);
      
    }
    
    private void init_launcher() {
      assert this.launcher == null: "This is a bug.";
      assert this.implem_launcher == null: "This is a bug.";
      this.implem_launcher = this.implementation.make_launcher();
      if (this.implem_launcher == null) {
      	throw new RuntimeException("make_launcher() in general.BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
      }
      this.launcher = this.implem_launcher._newComponent(new BridgeImpl_launcher(), false);
      
    }
    
    private void init_envEco() {
      assert this.envEco == null: "This is a bug.";
      assert this.implem_envEco == null: "This is a bug.";
      this.implem_envEco = this.implementation.make_envEco();
      if (this.implem_envEco == null) {
      	throw new RuntimeException("make_envEco() in general.BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> should not return null.");
      }
      this.envEco = this.implem_envEco._newComponent(new BridgeImpl_envEco(), false);
      
    }
    
    protected void initParts() {
      init_ecoAE();
      init_fw();
      init_launcher();
      init_envEco();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final BigEco<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
    
    private EcoAgents.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecoAE;
    
    private EcoAgents<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem_ecoAE;
    
    private final class BridgeImpl_ecoAE implements EcoAgents.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      public final IGetThread threads() {
        return BigEco.ComponentImpl.this.launcher().threads();
      }
    }
    
    public final EcoAgents.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecoAE() {
      return this.ecoAE;
    }
    
    private Forward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> fw;
    
    private Forward<CycleAlert, Context, ContextUpdate, Push, Pull> implem_fw;
    
    private final class BridgeImpl_fw implements Forward.Requires<CycleAlert, Context, ContextUpdate, Push, Pull> {
      public final CycleAlert i() {
        return BigEco.ComponentImpl.this.launcher().finishedCycle();
      }
      
      public final Context j() {
        return BigEco.ComponentImpl.this.envEco().envInfos();
      }
      
      public final ContextUpdate k() {
        return BigEco.ComponentImpl.this.envEco().envUpdate();
      }
    }
    
    public final Forward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> fw() {
      return this.fw;
    }
    
    private Launcher.Component launcher;
    
    private Launcher implem_launcher;
    
    private final class BridgeImpl_launcher implements Launcher.Requires {
    }
    
    public final Launcher.Component launcher() {
      return this.launcher;
    }
    
    private Environnement.Component<Context, ContextUpdate> envEco;
    
    private Environnement<Context, ContextUpdate> implem_envEco;
    
    private final class BridgeImpl_envEco implements Environnement.Requires<Context, ContextUpdate> {
    }
    
    public final Environnement.Component<Context, ContextUpdate> envEco() {
      return this.envEco;
    }
  }
  
  public static class DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends BigEco.DynamicAssemblyAgentTransition.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoAgents.TransitionAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentT();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.TransForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW();
    }
    
    public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements BigEco.DynamicAssemblyAgentTransition.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, BigEco.DynamicAssemblyAgentTransition.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      private final BigEco.DynamicAssemblyAgentTransition.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
      
      private final BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
      
      public void start() {
        assert this.agentT != null: "This is a bug.";
        ((EcoAgents.TransitionAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>) this.agentT).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.TransForward.ComponentImpl<CycleAlert, Context, ContextUpdate, Push, Pull>) this.aFW).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentT() {
        assert this.agentT == null: "This is a bug.";
        assert this.implementation.use_agentT != null: "This is a bug.";
        this.agentT = this.implementation.use_agentT._newComponent(new BridgeImpl_ecoAE_agentT(), false);
        
      }
      
      private void init_aFW() {
        assert this.aFW == null: "This is a bug.";
        assert this.implementation.use_aFW != null: "This is a bug.";
        this.aFW = this.implementation.use_aFW._newComponent(new BridgeImpl_fw_aFW(), false);
        
      }
      
      protected void initParts() {
        init_agentT();
        init_aFW();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final BigEco.DynamicAssemblyAgentTransition.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
      
      private EcoAgents.TransitionAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentT;
      
      private final class BridgeImpl_ecoAE_agentT implements EcoAgents.TransitionAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().a();
        }
        
        public final Context getContext() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().b();
        }
        
        public final ContextUpdate setContext() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().c();
        }
        
        public final Push push() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().d();
        }
        
        public final Pull pull() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().e();
        }
      }
      
      public final EcoAgents.TransitionAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentT() {
        return this.agentT;
      }
      
      private Forward.TransForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW;
      
      private final class BridgeImpl_fw_aFW implements Forward.TransForward.Requires<CycleAlert, Context, ContextUpdate, Push, Pull> {
      }
      
      public final Forward.TransForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW() {
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
    
    private BigEco.DynamicAssemblyAgentTransition.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
    
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
    protected BigEco.DynamicAssemblyAgentTransition.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
    protected BigEco.DynamicAssemblyAgentTransition.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
    protected BigEco.DynamicAssemblyAgentTransition.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private EcoAgents.TransitionAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> use_agentT;
    
    private Forward.TransForward<CycleAlert, Context, ContextUpdate, Push, Pull> use_aFW;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssemblyAgentTransition.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final BigEco.DynamicAssemblyAgentTransition.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssemblyAgentTransition has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssemblyAgentTransition.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new BigEco.DynamicAssemblyAgentTransition.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private BigEco.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected BigEco.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected BigEco.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    public interface Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> extends BigEco.DynamicAssemblyAgentEtat.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
    }
    
    public interface Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoAgents.StateAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentE();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.StateForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW();
    }
    
    public static class ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implements BigEco.DynamicAssemblyAgentEtat.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>, BigEco.DynamicAssemblyAgentEtat.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
      private final BigEco.DynamicAssemblyAgentEtat.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> bridge;
      
      private final BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implementation;
      
      public void start() {
        assert this.agentE != null: "This is a bug.";
        ((EcoAgents.StateAgent.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>) this.agentE).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.StateForward.ComponentImpl<CycleAlert, Context, ContextUpdate, Push, Pull>) this.aFW).start();
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
      
      public ComponentImpl(final BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem, final BigEco.DynamicAssemblyAgentEtat.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean doInits) {
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
      
      private EcoAgents.StateAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentE;
      
      private final class BridgeImpl_ecoAE_agentE implements EcoAgents.StateAgent.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().a();
        }
        
        public final Context getContext() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().b();
        }
        
        public final ContextUpdate setContext() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().c();
        }
        
        public final Push push() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().d();
        }
        
        public final Pull pull() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().e();
        }
      }
      
      public final EcoAgents.StateAgent.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> agentE() {
        return this.agentE;
      }
      
      private Forward.StateForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW;
      
      private final class BridgeImpl_fw_aFW implements Forward.StateForward.Requires<CycleAlert, Context, ContextUpdate, Push, Pull> {
      }
      
      public final Forward.StateForward.Component<CycleAlert, Context, ContextUpdate, Push, Pull> aFW() {
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
    
    private BigEco.DynamicAssemblyAgentEtat.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
    
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
    protected BigEco.DynamicAssemblyAgentEtat.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
    protected BigEco.DynamicAssemblyAgentEtat.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
    protected BigEco.DynamicAssemblyAgentEtat.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private EcoAgents.StateAgent<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> use_agentE;
    
    private Forward.StateForward<CycleAlert, Context, ContextUpdate, Push, Pull> use_aFW;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssemblyAgentEtat.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final BigEco.DynamicAssemblyAgentEtat.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssemblyAgentEtat has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssemblyAgentEtat.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new BigEco.DynamicAssemblyAgentEtat.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private BigEco.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected BigEco.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected BigEco.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> eco_parts() {
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
  
  private BigEco.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> selfComponent;
  
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
  protected BigEco.Provides<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> provides() {
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
  protected BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> requires() {
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
  protected BigEco.Parts<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> parts() {
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
  protected abstract EcoAgents<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> make_ecoAE();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<CycleAlert, Context, ContextUpdate, Push, Pull> make_fw();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Launcher make_launcher();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Environnement<Context, ContextUpdate> make_envEco();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized BigEco.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _newComponent(final BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BigEco has already been used to create a component, use another one.");
    }
    this.init = true;
    BigEco.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>  _comp = new BigEco.ComponentImpl<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> make_DynamicAssemblyAgentTransition(final String id) {
    return new BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _createImplementationOfDynamicAssemblyAgentTransition(final String id) {
    BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem = make_DynamicAssemblyAgentTransition(id);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentTransition() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentT == null: "This is a bug.";
    implem.use_agentT = this.selfComponent.implem_ecoAE._createImplementationOfTransitionAgent(id);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfTransForward(id);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentTransition.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> newDynamicAssemblyAgentTransition(final String id) {
    BigEco.DynamicAssemblyAgentTransition<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _implem = _createImplementationOfDynamicAssemblyAgentTransition(id);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentTransition.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> make_DynamicAssemblyAgentEtat(final String id) {
    return new BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _createImplementationOfDynamicAssemblyAgentEtat(final String id) {
    BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> implem = make_DynamicAssemblyAgentEtat(id);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentEtat() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentE == null: "This is a bug.";
    implem.use_agentE = this.selfComponent.implem_ecoAE._createImplementationOfStateAgent(id);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfStateForward(id);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentEtat.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> newDynamicAssemblyAgentEtat(final String id) {
    BigEco.DynamicAssemblyAgentEtat<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> _implem = _createImplementationOfDynamicAssemblyAgentEtat(id);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentEtat.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BigEco.Component<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull> newComponent() {
    return this._newComponent(new BigEco.Requires<ActionableState, ActionableTransition, Context, ContextUpdate, StateSharedMemory, TransSharedMemory, Push, Pull>() {}, true);
  }
}
