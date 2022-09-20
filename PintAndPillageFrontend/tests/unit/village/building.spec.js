import {createLocalVue, mount, shallowMount} from "@vue/test-utils";
import Vuex from "vuex";
import Farm from "@/components/tiles/Farm";
import ConstructionTile from "@/components/tiles/ConstructionTile";

let buildingWrapper;
let store;
let getters;
const localVue = createLocalVue()
localVue.component('construction-tile', ConstructionTile)
localVue.use(Vuex)

// beforeEach(() => {
//
// });

function setup(level) {
    getters = {
        seasonsOn: () => {
            return true
        },
        currentSeason: () => {
            return 'winter'
        },
        buildingProperties: () => {
            return {
                level,
                isUnderConstruction: false
            }
        }
    }

    const state = {
        buildingProperties: {
            level,
            isUnderConstruction: false
        }
    }

    store = new Vuex.Store({
        state,
        getters,
    })

    buildingWrapper = mount(Farm, {
        store,
        localVue,
        propsData: {
            buildingProperties: {
                level,
                isUnderConstruction: false
            }
        }
    });
}
afterAll(() =>{
    buildingWrapper.destroy()
})

describe('Building', () => {
    it('should show visual change on level 10', async () => {
        setup(1)

        const nilsepils = buildingWrapper.findAll(".tileImg").at(0).html()
        console.log(nilsepils)
        //   <img src="" class="tileImg">
        // TODO: Vincent ff vragen
        expect(false).toBeTruthy();
    })

});