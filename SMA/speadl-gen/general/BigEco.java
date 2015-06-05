package general;

import agents.interfaces.IGetThread;
import environnement.interfaces.CellInfo;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.EcoAgentsEtat;
import general.Environnement;
import general.Forward;
import general.Forward2;
import general.ForwardParam;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import java.util.List;
import trace.Action;

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
    public Forward.Component<EnvInfos> fwEnvInfos();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward.Component<EnvUpdate> fwEnvUpdate();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ForwardParam.Component<CellInfo> fwCellInfo();
    
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
    public Environnement.Component envEco();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward2.Component<CellInfo> fwEnvToCell();
  }
  
  public static class ComponentImpl implements BigEco.Component, BigEco.Parts {
    private final BigEco.Requires bridge;
    
    private final BigEco implementation;
    
    public void start() {
      assert this.ecoAE != null: "This is a bug.";
      ((EcoAgentsEtat.ComponentImpl) this.ecoAE).start();
      assert this.fw != null: "This is a bug.";
      ((Forward.ComponentImpl<CycleAlert>) this.fw).start();
      assert this.fwEnvInfos != null: "This is a bug.";
      ((Forward.ComponentImpl<EnvInfos>) this.fwEnvInfos).start();
      assert this.fwEnvUpdate != null: "This is a bug.";
      ((Forward.ComponentImpl<EnvUpdate>) this.fwEnvUpdate).start();
      assert this.fwCellInfo != null: "This is a bug.";
      ((ForwardParam.ComponentImpl<CellInfo>) this.fwCellInfo).start();
      assert this.launcher != null: "This is a bug.";
      ((Launcher.ComponentImpl) this.launcher).start();
      assert this.envEco != null: "This is a bug.";
      ((Environnement.ComponentImpl) this.envEco).start();
      assert this.fwEnvToCell != null: "This is a bug.";
      ((Forward2.ComponentImpl<CellInfo>) this.fwEnvToCell).start();
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
    
    private void init_fwEnvInfos() {
      assert this.fwEnvInfos == null: "This is a bug.";
      assert this.implem_fwEnvInfos == null: "This is a bug.";
      this.implem_fwEnvInfos = this.implementation.make_fwEnvInfos();
      if (this.implem_fwEnvInfos == null) {
      	throw new RuntimeException("make_fwEnvInfos() in general.BigEco should not return null.");
      }
      this.fwEnvInfos = this.implem_fwEnvInfos._newComponent(new BridgeImpl_fwEnvInfos(), false);
      
    }
    
    private void init_fwEnvUpdate() {
      assert this.fwEnvUpdate == null: "This is a bug.";
      assert this.implem_fwEnvUpdate == null: "This is a bug.";
      this.implem_fwEnvUpdate = this.implementation.make_fwEnvUpdate();
      if (this.implem_fwEnvUpdate == null) {
      	throw new RuntimeException("make_fwEnvUpdate() in general.BigEco should not return null.");
      }
      this.fwEnvUpdate = this.implem_fwEnvUpdate._newComponent(new BridgeImpl_fwEnvUpdate(), false);
      
    }
    
    private void init_fwCellInfo() {
      assert this.fwCellInfo == null: "This is a bug.";
      assert this.implem_fwCellInfo == null: "This is a bug.";
      this.implem_fwCellInfo = this.implementation.make_fwCellInfo();
      if (this.implem_fwCellInfo == null) {
      	throw new RuntimeException("make_fwCellInfo() in general.BigEco should not return null.");
      }
      this.fwCellInfo = this.implem_fwCellInfo._newComponent(new BridgeImpl_fwCellInfo(), false);
      
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
    
    private void init_fwEnvToCell() {
      assert this.fwEnvToCell == null: "This is a bug.";
      assert this.implem_fwEnvToCell == null: "This is a bug.";
      this.implem_fwEnvToCell = this.implementation.make_fwEnvToCell();
      if (this.implem_fwEnvToCell == null) {
      	throw new RuntimeException("make_fwEnvToCell() in general.BigEco should not return null.");
      }
      this.fwEnvToCell = this.implem_fwEnvToCell._newComponent(new BridgeImpl_fwEnvToCell(), false);
      
    }
    
    protected void initParts() {
      init_ecoAE();
      init_fw();
      init_fwEnvInfos();
      init_fwEnvUpdate();
      init_fwCellInfo();
      init_launcher();
      init_envEco();
      init_fwEnvToCell();
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
    
    private Forward.Component<EnvInfos> fwEnvInfos;
    
    private Forward<EnvInfos> implem_fwEnvInfos;
    
    private final class BridgeImpl_fwEnvInfos implements Forward.Requires<EnvInfos> {
      public final EnvInfos i() {
        return BigEco.ComponentImpl.this.envEco().envInfos();
      }
    }
    
    public final Forward.Component<EnvInfos> fwEnvInfos() {
      return this.fwEnvInfos;
    }
    
    private Forward.Component<EnvUpdate> fwEnvUpdate;
    
    private Forward<EnvUpdate> implem_fwEnvUpdate;
    
    private final class BridgeImpl_fwEnvUpdate implements Forward.Requires<EnvUpdate> {
      public final EnvUpdate i() {
        return BigEco.ComponentImpl.this.envEco().envUpdate();
      }
    }
    
    public final Forward.Component<EnvUpdate> fwEnvUpdate() {
      return this.fwEnvUpdate;
    }
    
    private ForwardParam.Component<CellInfo> fwCellInfo;
    
    private ForwardParam<CellInfo> implem_fwCellInfo;
    
    private final class BridgeImpl_fwCellInfo implements ForwardParam.Requires<CellInfo> {
      public final CellInfo i() {
        return BigEco.ComponentImpl.this.envEco().getCellInfo();
      }
    }
    
    public final ForwardParam.Component<CellInfo> fwCellInfo() {
      return this.fwCellInfo;
    }
    
    private Launcher.Component launcher;
    
    private Launcher implem_launcher;
    
    private final class BridgeImpl_launcher implements Launcher.Requires {
    }
    
    public final Launcher.Component launcher() {
      return this.launcher;
    }
    
    private Environnement.Component envEco;
    
    private Environnement implem_envEco;
    
    private final class BridgeImpl_envEco implements Environnement.Requires {
    }
    
    public final Environnement.Component envEco() {
      return this.envEco;
    }
    
    private Forward2.Component<CellInfo> fwEnvToCell;
    
    private Forward2<CellInfo> implem_fwEnvToCell;
    
    private final class BridgeImpl_fwEnvToCell implements Forward2.Requires<CellInfo> {
    }
    
    public final Forward2.Component<CellInfo> fwEnvToCell() {
      return this.fwEnvToCell;
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
      public EcoAgentsEtat.AgentEtat.Component agentE();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.Agent.Component<CycleAlert> aFW();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.Agent.Component<EnvInfos> afwEnvInfos();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward.Agent.Component<EnvUpdate> afwEnvUpdate();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ForwardParam.Agent.Component<CellInfo> afwCellInfo();
    }
    
    public static class ComponentImpl implements BigEco.DynamicAssemblyAgentEtat.Component, BigEco.DynamicAssemblyAgentEtat.Parts {
      private final BigEco.DynamicAssemblyAgentEtat.Requires bridge;
      
      private final BigEco.DynamicAssemblyAgentEtat implementation;
      
      public void start() {
        assert this.agentE != null: "This is a bug.";
        ((EcoAgentsEtat.AgentEtat.ComponentImpl) this.agentE).start();
        assert this.aFW != null: "This is a bug.";
        ((Forward.Agent.ComponentImpl<CycleAlert>) this.aFW).start();
        assert this.afwEnvInfos != null: "This is a bug.";
        ((Forward.Agent.ComponentImpl<EnvInfos>) this.afwEnvInfos).start();
        assert this.afwEnvUpdate != null: "This is a bug.";
        ((Forward.Agent.ComponentImpl<EnvUpdate>) this.afwEnvUpdate).start();
        assert this.afwCellInfo != null: "This is a bug.";
        ((ForwardParam.Agent.ComponentImpl<CellInfo>) this.afwCellInfo).start();
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
      
      private void init_afwEnvInfos() {
        assert this.afwEnvInfos == null: "This is a bug.";
        assert this.implementation.use_afwEnvInfos != null: "This is a bug.";
        this.afwEnvInfos = this.implementation.use_afwEnvInfos._newComponent(new BridgeImpl_fwEnvInfos_afwEnvInfos(), false);
        
      }
      
      private void init_afwEnvUpdate() {
        assert this.afwEnvUpdate == null: "This is a bug.";
        assert this.implementation.use_afwEnvUpdate != null: "This is a bug.";
        this.afwEnvUpdate = this.implementation.use_afwEnvUpdate._newComponent(new BridgeImpl_fwEnvUpdate_afwEnvUpdate(), false);
        
      }
      
      private void init_afwCellInfo() {
        assert this.afwCellInfo == null: "This is a bug.";
        assert this.implementation.use_afwCellInfo != null: "This is a bug.";
        this.afwCellInfo = this.implementation.use_afwCellInfo._newComponent(new BridgeImpl_fwCellInfo_afwCellInfo(), false);
        
      }
      
      protected void initParts() {
        init_agentE();
        init_aFW();
        init_afwEnvInfos();
        init_afwEnvUpdate();
        init_afwCellInfo();
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
      
      private EcoAgentsEtat.AgentEtat.Component agentE;
      
      private final class BridgeImpl_ecoAE_agentE implements EcoAgentsEtat.AgentEtat.Requires {
        public final CycleAlert finishedCycle() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.aFW().a();
        }
        
        public final EnvInfos getEnvInfos() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.afwEnvInfos().a();
        }
        
        public final EnvUpdate setEnv() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.afwEnvUpdate().a();
        }
        
        public final CellInfo getCellInfo() {
          return BigEco.DynamicAssemblyAgentEtat.ComponentImpl.this.afwCellInfo().a();
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
      
      private Forward.Agent.Component<EnvInfos> afwEnvInfos;
      
      private final class BridgeImpl_fwEnvInfos_afwEnvInfos implements Forward.Agent.Requires<EnvInfos> {
      }
      
      public final Forward.Agent.Component<EnvInfos> afwEnvInfos() {
        return this.afwEnvInfos;
      }
      
      private Forward.Agent.Component<EnvUpdate> afwEnvUpdate;
      
      private final class BridgeImpl_fwEnvUpdate_afwEnvUpdate implements Forward.Agent.Requires<EnvUpdate> {
      }
      
      public final Forward.Agent.Component<EnvUpdate> afwEnvUpdate() {
        return this.afwEnvUpdate;
      }
      
      private ForwardParam.Agent.Component<CellInfo> afwCellInfo;
      
      private final class BridgeImpl_fwCellInfo_afwCellInfo implements ForwardParam.Agent.Requires<CellInfo> {
      }
      
      public final ForwardParam.Agent.Component<CellInfo> afwCellInfo() {
        return this.afwCellInfo;
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
    
    private EcoAgentsEtat.AgentEtat use_agentE;
    
    private Forward.Agent<CycleAlert> use_aFW;
    
    private Forward.Agent<EnvInfos> use_afwEnvInfos;
    
    private Forward.Agent<EnvUpdate> use_afwEnvUpdate;
    
    private ForwardParam.Agent<CellInfo> use_afwCellInfo;
    
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
  
  public static class DynamicAssemblyAgentCell {
    public interface Requires {
    }
    
    public interface Component extends BigEco.DynamicAssemblyAgentCell.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Environnement.Cell.Component agentCell();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Forward2.Agent.Component<CellInfo> aFwCellInfos();
    }
    
    public static class ComponentImpl implements BigEco.DynamicAssemblyAgentCell.Component, BigEco.DynamicAssemblyAgentCell.Parts {
      private final BigEco.DynamicAssemblyAgentCell.Requires bridge;
      
      private final BigEco.DynamicAssemblyAgentCell implementation;
      
      public void start() {
        assert this.agentCell != null: "This is a bug.";
        ((Environnement.Cell.ComponentImpl) this.agentCell).start();
        assert this.aFwCellInfos != null: "This is a bug.";
        ((Forward2.Agent.ComponentImpl<CellInfo>) this.aFwCellInfos).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_agentCell() {
        assert this.agentCell == null: "This is a bug.";
        assert this.implementation.use_agentCell != null: "This is a bug.";
        this.agentCell = this.implementation.use_agentCell._newComponent(new BridgeImpl_envEco_agentCell(), false);
        
      }
      
      private void init_aFwCellInfos() {
        assert this.aFwCellInfos == null: "This is a bug.";
        assert this.implementation.use_aFwCellInfos != null: "This is a bug.";
        this.aFwCellInfos = this.implementation.use_aFwCellInfos._newComponent(new BridgeImpl_fwEnvToCell_aFwCellInfos(), false);
        
      }
      
      protected void initParts() {
        init_agentCell();
        init_aFwCellInfos();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final BigEco.DynamicAssemblyAgentCell implem, final BigEco.DynamicAssemblyAgentCell.Requires b, final boolean doInits) {
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
      
      private Environnement.Cell.Component agentCell;
      
      private final class BridgeImpl_envEco_agentCell implements Environnement.Cell.Requires {
      }
      
      public final Environnement.Cell.Component agentCell() {
        return this.agentCell;
      }
      
      private Forward2.Agent.Component<CellInfo> aFwCellInfos;
      
      private final class BridgeImpl_fwEnvToCell_aFwCellInfos implements Forward2.Agent.Requires<CellInfo> {
        public final CellInfo a() {
          return BigEco.DynamicAssemblyAgentCell.ComponentImpl.this.agentCell().cellInfos();
        }
      }
      
      public final Forward2.Agent.Component<CellInfo> aFwCellInfos() {
        return this.aFwCellInfos;
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
    
    private BigEco.DynamicAssemblyAgentCell.ComponentImpl selfComponent;
    
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
    protected BigEco.DynamicAssemblyAgentCell.Provides provides() {
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
    protected BigEco.DynamicAssemblyAgentCell.Requires requires() {
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
    protected BigEco.DynamicAssemblyAgentCell.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private Environnement.Cell use_agentCell;
    
    private Forward2.Agent<CellInfo> use_aFwCellInfos;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized BigEco.DynamicAssemblyAgentCell.Component _newComponent(final BigEco.DynamicAssemblyAgentCell.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DynamicAssemblyAgentCell has already been used to create a component, use another one.");
      }
      this.init = true;
      BigEco.DynamicAssemblyAgentCell.ComponentImpl  _comp = new BigEco.DynamicAssemblyAgentCell.ComponentImpl(this, b, true);
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
  protected abstract Forward<EnvInfos> make_fwEnvInfos();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<EnvUpdate> make_fwEnvUpdate();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ForwardParam<CellInfo> make_fwCellInfo();
  
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
  protected abstract Environnement make_envEco();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward2<CellInfo> make_fwEnvToCell();
  
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
  protected BigEco.DynamicAssemblyAgentEtat make_DynamicAssemblyAgentEtat(final String id, final String username, final List<Action> actions) {
    return new BigEco.DynamicAssemblyAgentEtat();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentEtat _createImplementationOfDynamicAssemblyAgentEtat(final String id, final String username, final List<Action> actions) {
    BigEco.DynamicAssemblyAgentEtat implem = make_DynamicAssemblyAgentEtat(id,username,actions);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentEtat() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ecoAE != null: "This is a bug.";
    assert implem.use_agentE == null: "This is a bug.";
    implem.use_agentE = this.selfComponent.implem_ecoAE._createImplementationOfAgentEtat(id,username);
    assert this.selfComponent.implem_fw != null: "This is a bug.";
    assert implem.use_aFW == null: "This is a bug.";
    implem.use_aFW = this.selfComponent.implem_fw._createImplementationOfAgent();
    assert this.selfComponent.implem_fwEnvInfos != null: "This is a bug.";
    assert implem.use_afwEnvInfos == null: "This is a bug.";
    implem.use_afwEnvInfos = this.selfComponent.implem_fwEnvInfos._createImplementationOfAgent();
    assert this.selfComponent.implem_fwEnvUpdate != null: "This is a bug.";
    assert implem.use_afwEnvUpdate == null: "This is a bug.";
    implem.use_afwEnvUpdate = this.selfComponent.implem_fwEnvUpdate._createImplementationOfAgent();
    assert this.selfComponent.implem_fwCellInfo != null: "This is a bug.";
    assert implem.use_afwCellInfo == null: "This is a bug.";
    implem.use_afwCellInfo = this.selfComponent.implem_fwCellInfo._createImplementationOfAgent(actions);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentEtat.Component newDynamicAssemblyAgentEtat(final String id, final String username, final List<Action> actions) {
    BigEco.DynamicAssemblyAgentEtat _implem = _createImplementationOfDynamicAssemblyAgentEtat(id,username,actions);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentEtat.Requires() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BigEco.DynamicAssemblyAgentCell make_DynamicAssemblyAgentCell(final List<Action> actions) {
    return new BigEco.DynamicAssemblyAgentCell();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BigEco.DynamicAssemblyAgentCell _createImplementationOfDynamicAssemblyAgentCell(final List<Action> actions) {
    BigEco.DynamicAssemblyAgentCell implem = make_DynamicAssemblyAgentCell(actions);
    if (implem == null) {
    	throw new RuntimeException("make_DynamicAssemblyAgentCell() in general.BigEco should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_envEco != null: "This is a bug.";
    assert implem.use_agentCell == null: "This is a bug.";
    implem.use_agentCell = this.selfComponent.implem_envEco._createImplementationOfCell(actions);
    assert this.selfComponent.implem_fwEnvToCell != null: "This is a bug.";
    assert implem.use_aFwCellInfos == null: "This is a bug.";
    implem.use_aFwCellInfos = this.selfComponent.implem_fwEnvToCell._createImplementationOfAgent(actions);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected BigEco.DynamicAssemblyAgentCell.Component newDynamicAssemblyAgentCell(final List<Action> actions) {
    BigEco.DynamicAssemblyAgentCell _implem = _createImplementationOfDynamicAssemblyAgentCell(actions);
    return _implem._newComponent(new BigEco.DynamicAssemblyAgentCell.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BigEco.Component newComponent() {
    return this._newComponent(new BigEco.Requires() {}, true);
  }
}
