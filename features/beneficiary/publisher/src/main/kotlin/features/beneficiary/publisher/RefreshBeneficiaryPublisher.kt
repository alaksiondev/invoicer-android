package features.beneficiary.publisher

import foundation.events.EventAware
import foundation.events.EventPublisher

class RefreshBeneficiaryPublisher : EventAware<Unit> by EventPublisher()