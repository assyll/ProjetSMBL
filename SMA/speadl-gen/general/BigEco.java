package general;

import agents.interfaces.IGetThread;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.ActionProvider;
import general.EcoAgents;
import general.Environnement;
import general.Forward;
import general.GraphComp;
import general.Launcher;
import general.LogComp;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;
import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;
import generalStructure.interfaces.IStop;
import trace.ActionTrace;
import trace.interfaces.ITakeAction;

@SuppressWarnings("all")
public abstract class BigEco {
  public interface Requires {
  }
  
  public interface Component extends BigEco.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ICreateAgent creatAgent();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public LogComp.Component logComp();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public GraphComp.Component graphComp();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EcoAgents.Component ecoAE();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> fw();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ActionProvider.Component actionProvider();
    
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
    public Environnement.Component<EnvInfos, EnvUpdate> envEco();
  }
  
  public static class ComponentImpl implements BigEco.Component, BigEco.Parts {
    private final BigEco.Requires bridge;
    
    private final BigEco implementation;
    
    public void start() {
      assert this.logComp != null: "This is a bug.";
      ((LogComp.ComponentImpl) this.logComp).start();
      assert this.graphComp != null: "This is a bug.";
      ((GraphComp.ComponentImpl) this.graphComp).start();
      assert this.ecoAE != null: "This is a bug.";
      ((EcoAgents.ComponentImpl) this.ecoAE).start();
      assert this.fw != null: "This is a bug.";
      ((Forward.ComponentImpl<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction>) this.fw).start();
      assert this.actionProvider != null: "This is a bug.";
      ((ActionProvider.ComponentImpl) this.actionProvider).start();
      assert this.launcher != null: "This is a bug.";
      ((Launcher.ComponentImpl) this.launcher).start();
      assert this.envEco != null: "This is a bug.";
      ((Environnement.ComponentImpl<EnvInfos, EnvUpdate>) this.envEco).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_logComp() {
      assert this.logComp == null: "This is a bug.";
      assert this.implem_logComp == null: "This is a bug.";
      this.implem_logComp = this.implementation.make_logComp();
      if (this.implem_logComp == null) {
      	throw new RuntimeException("make_logComp() in general.BigEco should not return null.");
      }
      this.logComp = this.implem_logComp._newComponent(new BridgeImpl_logComp(), false);
      
    }
    
    private void init_graphComp() {
      assert this.graphComp == null: "This is a bug.";
      assert this.implem_graphComp == null: "This is a bug.";
      this.implem_graphComp = this.implementation.make_graphComp();
      if (this.implem_graphComp == null) {
      	throw new RuntimeException("make_graphComp() in general.BigEco should not return null.");
      }
      this.graphComp = this.implem_graphComp._newComponent(new BridgeImpl_graphComp(), false);
      
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
    
    private void init_actionProvider() {
      assert this.actionProvider == null: "This is a bug.";
      assert this.implem_actionProvider == null: "This is a bug.";
      this.implem_actionProvider = this.implementation.make_actionProvider();
      if (this.implem_actionProvider == null) {
      	throw new RuntimeException("make_actionProvider() in general.BigEco should not return null.");
      }
      this.actionProvider = this.implem_actionProvider._newComponent(new BridgeImpl_actionProvider(), false);
      
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
    
    private void init_envEco() {
      assert this.envEco == null: "This is a bug.";
      assert this.implem_envEco == null: "This is a bug.";
      this.implem_envEco = this.implementation.make_envEco();
      if (this.implem_envEco == null) {
      	throw new RuntimeException("make_envEco() in general.BigEco should not return null.");
      }
      this.envEco = this.implem_envEco._newComponent(new BridgeImpl_envEco(), false);
      
    }
    
    protected void initParts() {
      init_logComp();
      init_graphComp();
      init_ecoAE();
      init_fw();
      init_actionProvider();
      init_launcher();
      init_envEco();
    }
    
    private void init_creatAgent() {
      assert this.creatAgent == null: "This is a bug.";
      this.creatAgent = this.implementation.make_creatAgent();
      if (this.creatAgent == null) {
      	throw new RuntimeException("make_creatAgent() in general.BigEco should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_creatAgent();
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
    
    private ICreateAgent creatAgent;
    
    public ICreateAgent creatAgent() {
      return this.creatAgent;
    }
    
    private LogComp.Component logComp;
    
    private LogComp implem_logComp;
    
    private final class BridgeImpl_logComp implements LogComp.Requires {
    }
    
    public final LogComp.Component logComp() {
      return this.logComp;
    }
    
    private GraphComp.Component graphComp;
    
    private GraphComp implem_graphComp;
    
    private final class BridgeImpl_graphComp implements GraphComp.Requires {
    }
    
    public final GraphComp.Component graphComp() {
      return this.graphComp;
    }
    
    private EcoAgents.Component ecoAE;
    
    private EcoAgents implem_ecoAE;
    
    private final class BridgeImpl_ecoAE implements EcoAgents.Requires {
      public final IGetThread threads() {
        return BigEco.ComponentImpl.this.launcher().threads();
      }
      
      public final ICreateAgent createAgent() {
        return BigEco.ComponentImpl.this.creatAgent();
      }
    }
    
    public final EcoAgents.Component ecoAE() {
      return this.ecoAE;
    }
    
    private Forward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> fw;
    
    private Forward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> implem_fw;
    
    private final class BridgeImpl_fw implements Forward.Requires<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> {
      public final CycleAlert i() {
        return BigEco.ComponentImpl.this.launcher().finishedCycle();
      }
      
      public final EnvInfos h() {
        return BigEco.ComponentImpl.this.envEco().envInfos();
      }
      
      public final EnvUpdate k() {
        return BigEco.ComponentImpl.this.envEco().envUpdate();
      }
      
      public final ITakeAction j() {
        return BigEco.ComponentImpl.this.actionProvider().actionGetter();
      }
      
      public final ILog log() {
        return BigEco.ComponentImpl.this.logComp().log();
      }
      
      public final IGraph graph() {
        return BigEco.ComponentImpl.this.graphComp().graph();
      }
    }
    
    public final Forward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> fw() {
      return this.fw;
    }
    
    private ActionProvider.Component actionProvider;
    
    private ActionProvider implem_actionProvider;
    
    private final class BridgeImpl_actionProvider implements ActionProvider.Requires {
    }
    
    public final ActionProvider.Component actionProvider() {
      return this.actionProvider;
    }
    
    private Launcher.Component launcher;
    
    private Launcher implem_launcher;
    
    private final class BridgeImpl_launcher implements Launcher.Requires {
      public final IStop stopProcessus() {
        return BigEco.ComponentImpl.this.fw().stopProcessus();
      }
    }
    
    public final Launcher.Component launcher() {
      return this.launcher;
    }
    
    private Environnement.Component<EnvInfos, EnvUpdate> envEco;
    
    private Environnement<EnvInfos, EnvUpdate> implem_envEco;
    
    private final class BridgeImpl_envEco implements Environnement.Requires<EnvInfos, EnvUpdate> {
    }
    
    public final Environnement.Component<EnvInfos, EnvUpdate> envEco() {
      return this.envEco;
    }
  }
  
  public static class DynamicAssemblyAgentTransition {
    public interface Requires {
    }
    
    public interface Component extends BigEco.DynamicAssemblyAgentTransition.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoAgents.TransitionAgent.Component agentT();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.TransForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW();
    }
    
    public static class ComponentImpl implements BigEco.DynamicAssemblyAgentTransition.Component, BigEco.DynamicAssemblyAgentTransition.Parts {
      private final BigEco.DynamicAssemblyAgentTransition.Requires bridge;
      
      private final BigEco.DynamicAssemblyAgentTransition implementation;
      
      public void start() {
        assert this.agentT != null: "This is a bug.";
        ((EcoAgents.TransitionAgent.ComponentImpl) this.agentT).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.TransForward.ComponentImpl<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction>) this.aFW).start();
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
      
      public ComponentImpl(final BigEco.DynamicAssemblyAgentTransition implem, final BigEco.DynamicAssemblyAgentTransition.Requires b, final boolean doInits) {
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
      
      private EcoAgents.TransitionAgent.Component agentT;
      
      private final class BridgeImpl_ecoAE_agentT implements EcoAgents.TransitionAgent.Requires {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().a();
        }
        
        public final EnvInfos getContext() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().b();
        }
        
        public final EnvUpdate setContext() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().c();
        }
        
        public final SendMessage push() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().d();
        }
        
        public final PullMessage pull() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().e();
        }
        
        public final ILog finishedCycleForLog() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().finishedCycleForLog();
        }
        
        public final IGraph graph() {
          return BigEco.DynamicAssemblyAgentTransition.ComponentImpl.this.aFW().graph();
        }
      }
      
      public final EcoAgents.TransitionAgent.Component agentT() {
        return this.agentT;
      }
      
      private Forward.TransForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW;
      
      private final class BridgeImpl_fw_aFW implements Forward.TransForward.Requires<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> {
      }
      
      public final Forward.TransForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW() {
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
    
    private BigEco.DynamicAssemblyAgentTransition.ComponentImpl selfComponent;
    
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
    protected BigEco.DynamicAssemblyAgentTransition.Provides provides() {
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
    protected BigEco.DynamicAssemblyAgentTransition.Requires requires() {
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
    protected BigEco.DynamicAssemblyAgentTransition.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private EcoAgents.TransitionAgent use_agentT;
    
    private Forward.TransForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> use_aFW;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssemblyAgentTransition.Component _newComponent(final BigEco.DynamicAssemblyAgentTransition.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssemblyAgentTransition has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssemblyAgentTransition.ComponentImpl  _comp = new BigEco.DynamicAssemblyAgentTransition.ComponentImpl(this, b, true);
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
  
  public static class DynamicAssemblyAgentEtat {
    public interface Requires {
    }
    
    public interface Component extends BigEco.DynamicAssemblyAgentEtat.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoAgents.StateAgent.Component agentE();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.StateForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW();
    }
    
    public static class ComponentImpl implements BigEco.DynamicAssemblyAgentEtat.Component, BigEco.DynamicAssemblyAgentEtat.Parts {
      private final BigEco.DynamicAssemblyAgentEtat.Requires bridge;
      
      private final BigEco.DynamicAssemblyAgentEtat implementation;
      
      public void start() {
        assert this.agentE != null: "This is a bug.";
        ((EcoAgents.StateAgent.ComponentImpl) this.agentE).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.StateForward.ComponentImpl<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction>) this.aFW).start();
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
      
      public ComponentImpl(final BigEco.DynamicAssemblyAgentEtat implem, final BigEco.DynamicAssemblyAgentEtat.Requires b, final boolean doInits) {
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
      
      private EcoAgents.StateAgent.Component agentE;
      
      private final class BridgeImpl_ecoAE_agentE implements EcoAgents.StateAgent.Requires {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().a();
        }
        
        public final ContextInfos getContext() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().b();
        }
        
        public final EnvUpdate setContext() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().c();
        }
        
        public final SendMessage push() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().d();
        }
        
        public final PullMessage pull() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().e();
        }
        
        public final ILog finishedCycleForLog() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().finishedCycleForLog();
        }
        
        public final IGraph graph() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().graph();
        }
      }
      
      public final EcoAgents.StateAgent.Component agentE() {
        return this.agentE;
      }
      
      private Forward.StateForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW;
      
      private final class BridgeImpl_fw_aFW implements Forward.StateForward.Requires<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> {
      }
      
      public final Forward.StateForward.Component<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> aFW() {
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
    
    private BigEco.DynamicAssemblyAgentEtat.ComponentImpl selfComponent;
    
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
    protected BigEco.DynamicAssemblyAgentEtat.Provides provides() {
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
    protected BigEco.DynamicAssemblyAgentEtat.Requires requires() {
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
    protected BigEco.DynamicAssemblyAgentEtat.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private EcoAgents.StateAgent use_agentE;
    
    private Forward.StateForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> use_aFW;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssemblyAgentEtat.Component _newComponent(final BigEco.DynamicAssemblyAgentEtat.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssemblyAgentEtat has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssemblyAgentEtat.ComponentImpl  _comp = new BigEco.DynamicAssemblyAgentEtat.ComponentImpl(this, b, true);
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
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ICreateAgent make_creatAgent();
  
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
  protected abstract LogComp make_logComp();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract GraphComp make_graphComp();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract EcoAgents make_ecoAE();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> make_fw();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ActionProvider make_actionProvider();
  
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
  protected abstract Environnement<EnvInfos, EnvUpdate> make_envEco();
  
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
  protected BigEco.DynamicAssemblyAgentTransition make_DynamicAssemblyAgentTransition(final String id, final ActionTrace action, final String idSource, final String idCible, final boolean createCible) {
    return new BigEco.DynamicAssemblyAgentTransition();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentTransition _createImplementationOfDynamicAssemblyAgentTransition(final String id, final ActionTrace action, final String idSource, final String idCible, final boolean createCible) {
    BigEco.DynamicAssemblyAgentTransition implem = make_DynamicAssemblyAgentTransition(id,action,idSource,idCible,createCible);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentTransition() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentT == null: "This is a bug.";
    implem.use_agentT = this.selfComponent.implem_ecoAE._createImplementationOfTransitionAgent(id,action,idSource,idCible,createCible);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfTransForward(id);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentTransition.Component newDynamicAssemblyAgentTransition(final String id, final ActionTrace action, final String idSource, final String idCible, final boolean createCible) {
    BigEco.DynamicAssemblyAgentTransition _implem = _createImplementationOfDynamicAssemblyAgentTransition(id,action,idSource,idCible,createCible);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentTransition.Requires() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BigEco.DynamicAssemblyAgentEtat make_DynamicAssemblyAgentEtat(final String id, final boolean isRoot) {
    return new BigEco.DynamicAssemblyAgentEtat();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentEtat _createImplementationOfDynamicAssemblyAgentEtat(final String id, final boolean isRoot) {
    BigEco.DynamicAssemblyAgentEtat implem = make_DynamicAssemblyAgentEtat(id,isRoot);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentEtat() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentE == null: "This is a bug.";
    implem.use_agentE = this.selfComponent.implem_ecoAE._createImplementationOfStateAgent(id,isRoot);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfStateForward(id,isRoot);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentEtat.Component newDynamicAssemblyAgentEtat(final String id, final boolean isRoot) {
    BigEco.DynamicAssemblyAgentEtat _implem = _createImplementationOfDynamicAssemblyAgentEtat(id,isRoot);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentEtat.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BigEco.Component newComponent() {
    return this._newComponent(new BigEco.Requires() {}, true);
  }
}
