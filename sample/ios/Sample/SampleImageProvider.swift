import UIKit
import TrikotViewmodelsDeclarativeSample

class SampleImageProvider: VMDImageProvider {
    func imageNameForResource(imageResource: VMDImageResource) -> String? {
        guard let resource = imageResource as? SampleImageResource else { return nil }
        switch resource {
        case .iconClose:
            return "icn_close"
        default:
            return nil
        }
    }
}
