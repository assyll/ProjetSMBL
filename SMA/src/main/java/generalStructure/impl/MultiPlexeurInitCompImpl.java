package generalStructure.impl;

import general.MultiPlexeurInitComp;
import generalStructure.interfaces.IInit;

public class MultiPlexeurInitCompImpl extends MultiPlexeurInitComp {

	@Override
	protected IInit make_initLauncher() {
		return new IInit() {
			@Override
			public void init() {
				requires().initActionProvider().init();
				requires().initEcoAgent().init();
				requires().initEnvironnement().init();
				requires().initForward().init();
				requires().initGraph().init();
				requires().initLog().init();
			}
		};
	}

}
