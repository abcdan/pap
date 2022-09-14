import LevelUpBuilding from "@/components/ui/LevelUpBuilding";
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Vuex from 'vuex'

const localVue = createLocalVue()

localVue.use(Vuex)
describe('LevelUpBuilding', function () {
    let levelUpBuildingWrapper;

    beforeEach(() => {
        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            mocks: {
                $store: {
                    getters: {
                        building: () => {
                            return { isUnderConstruction: true }
                        },
                    }
                }
            },
            localVue
        })
    });
    it('should level up the building', function () {
        const message = levelUpBuildingWrapper.find('#building-is-under-construction')

        expect(message.element.textContent).toBe("Building is under construction")
    });
});