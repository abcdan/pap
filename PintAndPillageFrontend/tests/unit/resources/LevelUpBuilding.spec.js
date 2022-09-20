import {createLocalVue, ErrorWrapper, shallowMount} from "@vue/test-utils";
import Vuex from "vuex";
import LevelUpBuilding from "@/components/ui/LevelUpBuilding";
import TimeFrame from "@/components/ui/TimeFrame";
import ResourceItem from "@/components/ui/ResourceItem";
import PopulationFrame from "@/components/ui/PopulationFrame";
import BUILDING from "../mockData/test_building_data_not_under_construction.json";
import VILLAGE from "../mockData/test_village_data.json";

let levelUpBuildingWrapper;
let store;
let getters;
const localVue = createLocalVue()
localVue.component('time-frame', TimeFrame)
localVue.component('population-frame', PopulationFrame)
localVue.component('resource-item', ResourceItem)
localVue.use(Vuex)


const state = {
    village: {
        data: require("../mockData/test_village_data.json")
    }
}

afterAll(() =>{
    levelUpBuildingWrapper.destroy()
})

describe('LevelUpBuilding', () => {
    it('is under construction', function () {
        getters = {
            building: () => () => {
                return require("../mockData/test_building_data.json")
            }
        }

        store = new Vuex.Store({
            getters,
            state
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });

        const message = levelUpBuildingWrapper.find('#building-is-under-construction').text()
        expect(message).toBe("Building is under construction")
    });

    it('is not under construction',  () => {

        getters = {
            building: () => () => {
                return require("../mockData/test_building_data_not_under_construction.json")
            }
        }

        store = new Vuex.Store({
            getters,
            state
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });


        const message = levelUpBuildingWrapper.find('#building-is-under-construction')

        // console.log(message)
        expect(message).toBeInstanceOf(ErrorWrapper)
    });

    it('enough resources to level up',  () => {

        const BUILDING =  require("../mockData/test_building_data_not_under_construction.json")
        BUILDING.resourcesRequiredLevelUp.Stone = 10
        BUILDING.resourcesRequiredLevelUp.Hop = 0
        BUILDING.resourcesRequiredLevelUp.Wood = 0

        const VILLAGE =  require("../mockData/test_village_data.json")
        VILLAGE.villageResources.Stone = 12
        VILLAGE.villageResources.Hop = 0
        VILLAGE.villageResources.Wood = 0

        getters = {
            building: () => () => {
                return BUILDING
            }
        }

        store = new Vuex.Store({
            getters,
            state: {
                village: {
                    data: VILLAGE
                }
            }
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });



        const levelUpButton = levelUpBuildingWrapper.find('#level-up-button')

        const IS_DISABLED = false;
        expect(levelUpButton.element.disabled).toBe(IS_DISABLED) // should be enabled
    });

    it('not enough resources to level up',  () => {

        const BUILDING =  require("../mockData/test_building_data_not_under_construction.json")
        BUILDING.resourcesRequiredLevelUp.Stone = 12
        BUILDING.resourcesRequiredLevelUp.Hop = 0
        BUILDING.resourcesRequiredLevelUp.Wood = 0

        const VILLAGE =  require("../mockData/test_village_data.json")
        VILLAGE.villageResources.Stone = 10
        VILLAGE.villageResources.Hop = 0
        VILLAGE.villageResources.Wood = 0

        getters = {
            building: () => () => {
                return BUILDING
            }
        }

        store = new Vuex.Store({
            getters,
            state: {
                village: {
                    data: VILLAGE
                }
            }
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });



        const levelUpButton = levelUpBuildingWrapper.find('#level-up-button')

        const IS_DISABLED = true;
        expect(levelUpButton.element.disabled).toBe(IS_DISABLED) // should be disabled
    });



    it('enough resources to level up multiple resources',  () => {

        const BUILDING =  require("../mockData/test_building_data_not_under_construction.json")
        BUILDING.resourcesRequiredLevelUp.Stone = 10
        BUILDING.resourcesRequiredLevelUp.Hop = 10
        BUILDING.resourcesRequiredLevelUp.Wood = 10

        const VILLAGE =  require("../mockData/test_village_data.json")
        VILLAGE.villageResources.Stone = 12
        VILLAGE.villageResources.Hop = 12
        VILLAGE.villageResources.Wood = 12

        getters = {
            building: () => () => {
                return BUILDING
            }
        }

        store = new Vuex.Store({
            getters,
            state: {
                village: {
                    data: VILLAGE
                }
            }
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });



        const levelUpButton = levelUpBuildingWrapper.find('#level-up-button')

        const IS_DISABLED = false;
        expect(levelUpButton.element.disabled).toBe(IS_DISABLED) // should be enabled
    });

    it('not resources to level up multiple resources',  () => {

        const BUILDING =  require("../mockData/test_building_data_not_under_construction.json")
        BUILDING.resourcesRequiredLevelUp.Stone = 22
        BUILDING.resourcesRequiredLevelUp.Hop = 22
        BUILDING.resourcesRequiredLevelUp.Wood = 22

        const VILLAGE =  require("../mockData/test_village_data.json")
        VILLAGE.villageResources.Stone = 12
        VILLAGE.villageResources.Hop = 12
        VILLAGE.villageResources.Wood = 12

        getters = {
            building: () => () => {
                return BUILDING
            }
        }

        store = new Vuex.Store({
            getters,
            state: {
                village: {
                    data: VILLAGE
                }
            }
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });



        const levelUpButton = levelUpBuildingWrapper.find('#level-up-button')

        const IS_DISABLED = true;
        expect(levelUpButton.element.disabled).toBe(IS_DISABLED) // should be disabled
    });

});