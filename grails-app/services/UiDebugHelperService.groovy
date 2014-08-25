

import com.mtvnet.scenic.entity.entities.CommonProperties
import com.mtvnet.scenic.entity.entities.INTLImageEntity


class UiDebugHelperService {
	def grailsApplication
	def siteResolverService
	static scope = "request"

	private Map _requestedObject = [:]

	Map getRequestedObjects() {
		return _requestedObject
	}

	void notifyGetItem(Object item) {
		if (item && !_requestedObject.containsKey(item.uuid)) {
			_requestedObject[item.uuid] = item
		}

	}


}